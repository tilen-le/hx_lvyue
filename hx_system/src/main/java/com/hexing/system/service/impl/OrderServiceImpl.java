package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.helper.LoginHelper;
import com.hexing.common.utils.JsonUtils;
import com.hexing.common.utils.StringUtils;
import com.hexing.system.domain.*;
import com.hexing.system.domain.form.*;
import com.hexing.system.domain.vo.PaymentClaimVO;
import com.hexing.system.mapper.*;
import com.hexing.system.service.IOrderService;
import com.hexing.system.service.ISysDataScopeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author firerock_tech
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<FcOrderMapper, FcOrder> implements IOrderService {

    private final FcOrderMapper baseMapper;
    private final FcContractMapper fcContractMapper;
    private final FcOrderProductMapper fcOrderProductMapper;
    private final FcOrderConsignmentMapper fcOrderConsignmentMapper;

    private final FcOrderPayMilestoneMapper fcOrderPayMilestoneMapper;
    private final FcPaymentClaimMapper fcPaymentClaimMapper;

    private final ISysDataScopeService sysDataScopeService;

    @Resource
    @Lazy
    private SyncService syncService;

    private final FcShippingPlanFinancialAccountingMapper planFinancialAccountingMapper;

    @Override
    public int saveOrder(ReciveOrderDTO orders) {
        if (ObjectUtil.isNotNull(orders)) {
            System.out.println(orders);
            if (ObjectUtil.isNotNull(orders)) {
                orders.getData().forEach(this::handleOrderStruct);
            }

            if (ObjectUtil.isNotNull(orders)) {
                orders.getPay().forEach(this::handleOrderMilestoneForm);
            }
        }
        return 0;
    }

    @Override
    public FcContract getContact(String cid) {
        LambdaQueryWrapper<FcContract> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcContract::getContractNumber, cid);
        return fcContractMapper.selectOne(queryWrapper);
    }

    /**
     * 计划回款金额 = 订单总金额*回款比例
     * 剩余回款金额 = 订单总金额-计划回款金额
     * 期待回款日期 = 订单sap创建日期+付款天数
     * *    "xh":"1", //序号
     * *    "zpayname":"到货日期", //
     * *    "zpayscale":"1.0", //期待回款比例
     * *    "vbeln":"PN23.04006", //订单编号
     * *    "zpayday":"90",  //付款天数
     * *    "zpaynode":"Z013", //里程碑编码
     * *    "zkxname":"到货款" //里程碑类型
     *
     * @param milestoneForm
     */
    public void handleOrderMilestoneForm(MilestoneForm milestoneForm) {
        log.error(JsonUtils.toJsonString(milestoneForm));
        LambdaQueryWrapper<FcOrderPayMilestone> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrderPayMilestone::getOrderNumber, milestoneForm.getVbeln())
                .eq(FcOrderPayMilestone::getSequence, milestoneForm.getXh());
        FcOrderPayMilestone payMilestone = fcOrderPayMilestoneMapper.selectOne(queryWrapper);
        FcOrder fcOrder = baseMapper.selectByOrderNumber(milestoneForm.getVbeln());
        String amount = fcOrder.getAmount();
        Date createTime = fcOrder.getSapCreateTime();
        Calendar c = Calendar.getInstance();
        c.setTime(createTime);
        c.add(Calendar.DATE, 5);
        Date date = c.getTime();
        queryWrapper.clear();
        queryWrapper.eq(FcOrderPayMilestone::getOrderNumber, milestoneForm.getVbeln());
        List<FcOrderPayMilestone> payMilestones = fcOrderPayMilestoneMapper.selectList(new LambdaQueryWrapper<FcOrderPayMilestone>()
                .eq(FcOrderPayMilestone::getOrderNumber, fcOrder.getOrderNumber())
                .le(FcOrderPayMilestone::getExpectPayDate, date));
        List<String> allPlanList = payMilestones.stream().map(FcOrderPayMilestone::getPlanPayAmount).collect(Collectors.toList());
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        allPlanList.forEach(item -> {
            Double s = Double.parseDouble(item);
            sum.updateAndGet(v -> v + s);
        });
        if (ObjectUtil.isNotNull(payMilestone)) {
            payMilestone.setCode(milestoneForm.getZpaynode());
            payMilestone.setType(milestoneForm.getZkxname());
            payMilestone.setExpectPayDate(date);
            payMilestone.setExpectPayScale(milestoneForm.getZpayscale());
            BigDecimal decimal = new BigDecimal(amount).multiply(new BigDecimal(milestoneForm.getZpayscale()));
            BigDecimal scale = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            double surplus = Double.parseDouble(amount) - sum.get() + Double.parseDouble(payMilestone.getPlanPayAmount());
            payMilestone.setPlanPayAmount(scale.toPlainString());
            double newSurplus = surplus - Double.parseDouble(payMilestone.getPlanPayAmount());
            payMilestone.setSurplusPayAmount(String.valueOf(newSurplus));
            fcOrderPayMilestoneMapper.updateById(payMilestone);
        } else {
            payMilestone = new FcOrderPayMilestone();
            payMilestone.setOrderNumber(milestoneForm.getVbeln());
            payMilestone.setSequence(milestoneForm.getXh());
            payMilestone.setCode(milestoneForm.getZpaynode());
            payMilestone.setType(milestoneForm.getZkxname());
            payMilestone.setExpectPayDate(date);
            payMilestone.setExpectPayScale(milestoneForm.getZpayscale());
            BigDecimal decimal = new BigDecimal(amount).multiply(new BigDecimal(milestoneForm.getZpayscale()));
            BigDecimal scale = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            payMilestone.setPlanPayAmount(scale.toPlainString());
            double surplus = Double.parseDouble(amount) - sum.get() - Double.parseDouble(payMilestone.getPlanPayAmount());
            payMilestone.setSurplusPayAmount(String.valueOf(surplus));
            fcOrderPayMilestoneMapper.insert(payMilestone);
        }
    }

    @Override
    public void handleOrderStruct(OrderForm orderForm) {
        log.error(JsonUtils.toJsonString(orderForm));
        LambdaQueryWrapper<FcOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrder::getOrderNumber, orderForm.getVbeln());
        FcOrder existOrder = baseMapper.selectOne(queryWrapper);
        String kunnrEr = orderForm.getKunnrEr();
        if (Objects.nonNull(kunnrEr) && kunnrEr.startsWith("00")) {
            kunnrEr = kunnrEr.substring(2, kunnrEr.length() - 1);
        }
        if (ObjectUtil.isNotNull(existOrder)) {
            existOrder.setContractNumber(orderForm.getVbelnRe());
            existOrder.setOrderNumber(orderForm.getVbeln());
            existOrder.setOrderTitle(orderForm.getVbelnT());
            existOrder.setCurrency(orderForm.getWaers());
            existOrder.setSapCreateTime(orderForm.getErdat());
            existOrder.setMarketingDepartment(orderForm.getZgnw());
            //售达方
            existOrder.setSoldToParty(orderForm.getKunnrSpT());
            existOrder.setSoldToPartyCd(orderForm.getKunnrSp());
            //送达方
            existOrder.setReciverCd(orderForm.getKunnrSh());
            existOrder.setReciver(orderForm.getKunnrShT());
            //付款方
            existOrder.setPayerCd(orderForm.getKunnrPy());
            existOrder.setPayer(orderForm.getKunnrPyT());
            //收票方
            existOrder.setBileeCd(orderForm.getKunnrBp());
            existOrder.setBillee(orderForm.getKunnrBpT());
            existOrder.setAmount(orderForm.getAmount());

            existOrder.setCustomerManagerNumber(kunnrEr);
            existOrder.setCustomerManager(orderForm.getKunnrErT());
            existOrder.setRequireDeliveryDate(orderForm.getVdatu());
            //销售组织
            existOrder.setSaleOrgCd(orderForm.getVkorg());
            existOrder.setSaleOrg(orderForm.getVtext());
            existOrder.setFactory(orderForm.getWerks());
            existOrder.setIsBackupTableDirectly(orderForm.getItext2());
            existOrder.setDistributionChannelCd(orderForm.getVtweg());
            existOrder.setDistributionChannel(orderForm.getVtwegT());
            existOrder.setBusinessUnitCd(orderForm.getZspare5());
            existOrder.setBusinessUnit(orderForm.getZspare5T());
            existOrder.setSaleTypeCd(orderForm.getAuart());
            existOrder.setSaleType(orderForm.getAuartT());
            //收款方
            existOrder.setPayeeCd(orderForm.getBukrs());
            existOrder.setPayee(orderForm.getButxt());
            baseMapper.updateById(existOrder);
        } else {
            existOrder = new FcOrder();
            existOrder.setContractNumber(orderForm.getVbelnRe());
            existOrder.setOrderNumber(orderForm.getVbeln());
            existOrder.setOrderTitle(orderForm.getVbelnT());
            existOrder.setCurrency(orderForm.getWaers());
            existOrder.setSapCreateTime(orderForm.getErdat());
            existOrder.setMarketingDepartment(orderForm.getZgnw());
            //售达方
            existOrder.setSoldToParty(orderForm.getKunnrSpT());
            existOrder.setSoldToPartyCd(orderForm.getKunnrSp());
            existOrder.setRequireDeliveryDate(orderForm.getVdatu());
            existOrder.setAmount(orderForm.getAmount());
            //送达方
            existOrder.setReciverCd(orderForm.getKunnrSh());
            existOrder.setReciver(orderForm.getKunnrShT());
            //付款方
            existOrder.setPayerCd(orderForm.getKunnrPy());
            existOrder.setPayer(orderForm.getKunnrPyT());
            existOrder.setFactory(orderForm.getWerks());
            existOrder.setCustomerManagerNumber(kunnrEr);
            existOrder.setCustomerManager(orderForm.getKunnrErT());
            //收票方
            existOrder.setBileeCd(orderForm.getKunnrBp());
            existOrder.setBillee(orderForm.getKunnrBpT());
            existOrder.setIsBackupTableDirectly(orderForm.getItext2());
            //销售组织
            existOrder.setSaleOrgCd(orderForm.getVkorg());
            existOrder.setSaleOrg(orderForm.getVtext());
            existOrder.setDistributionChannelCd(orderForm.getVtweg());
            existOrder.setDistributionChannel(orderForm.getVtwegT());
            existOrder.setBusinessUnitCd(orderForm.getZspare5());
            existOrder.setBusinessUnit(orderForm.getZspare5T());
            existOrder.setSaleTypeCd(orderForm.getAuart());
            existOrder.setSaleType(orderForm.getAuartT());
            //收款方
            existOrder.setPayeeCd(orderForm.getBukrs());
            existOrder.setPayee(orderForm.getButxt());
            baseMapper.insert(existOrder);
        }
        FcOrderProduct fcOrderProduct = fcOrderProductMapper.selectOne(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getOrderId, existOrder.getId()).eq(FcOrderProduct::getSapDetailNumber, orderForm.getPosnr()));
        if (ObjectUtil.isNull(fcOrderProduct)) {
            fcOrderProduct = new FcOrderProduct();
        }
        fcOrderProduct.setOrderId(existOrder.getId());
        fcOrderProduct.setOrderNumber(existOrder.getOrderNumber());
        fcOrderProduct.setNum(orderForm.getZmeng());
        fcOrderProduct.setProductNumber(orderForm.getMatnr());
        fcOrderProduct.setProductModel(orderForm.getZcpxh());
        fcOrderProduct.setProductName(orderForm.getMaktx());
        fcOrderProduct.setUnitPrice(orderForm.getNetprZpr0());
        fcOrderProduct.setTaxRate(orderForm.getZsl());
        fcOrderProduct.setSapDetailNumber(orderForm.getPosnr());
        //计算库存锁定数量
        String orderLineSum = fcOrderConsignmentMapper.getOrderLineSum(existOrder.getId(), orderForm.getPosnr());
        double v = Double.parseDouble(fcOrderProduct.getNum()) - Double.parseDouble(orderLineSum);
        fcOrderProduct.setNotSentNum(String.valueOf(v));
        fcOrderProductMapper.insertOrUpdate(fcOrderProduct);
        //初始状态更新库存
        syncService.getStore(orderForm);
    }


    @Override
    public TableDataInfo<FcOrder> listOrders(FcOrder fcOrder, PageQuery pageQuery) {
        //用户只可以查看属于自己部门的数据,管理员可查看全部
//        if (!LoginHelper.isAdmin()) {
//            Long deptId = LoginHelper.getDeptId();
//            fcOrder.setDeptId(deptId);
//            String deptAndChild = sysDataScopeService.getDeptAndChild(deptId);
//        }
        String orderNumber = fcOrder.getOrderNumber();
        if (Objects.nonNull(orderNumber)) {
            fcOrder.setOrderNumber(orderNumber.toUpperCase());
        }
        Page<FcOrder> page = baseMapper.selectPageOrderList(pageQuery.build(), fcOrder);
        page.getRecords().forEach(item -> {
            LambdaQueryWrapper<FcOrderProduct> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FcOrderProduct::getOrderId, item.getId());
            List<FcOrderProduct> products = fcOrderProductMapper.selectList(queryWrapper);
            if (products.size() > 0) {
                item.setHasChildren(true);
                item.setProducts(products);
                double sum = products.stream().mapToDouble(s -> Double.parseDouble(s.getNum())).sum();
                double sumInTransitNum = products.stream().mapToDouble(s -> Double.parseDouble(Objects.isNull(s.getInTransitNum()) ? "0.0" : s.getInTransitNum())).sum();
                item.setSum(String.valueOf(sum));
                item.setSumInTransitNum(String.valueOf(sumInTransitNum));
            }
        });
        return TableDataInfo.build(page);
    }

    /**
     * 发货状态：
     * 1.全部产品行都完成发货，未发货数量都为0，发货状态为发货完成
     * 2.部分发货是有产品行还有未发货数量，
     * 3.未发货是所有行项目已发都为0
     */
    public Integer getConsignmentStatus(Long orderId) {
        List<FcOrderProduct> products = fcOrderProductMapper.selectList(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getOrderId, orderId));
        boolean all = products.stream().allMatch(item -> Double.valueOf(item.getNotSentNum()).compareTo(0.0) == 0);
        if (all) {
            return 1;
        } else {
            boolean any = products.stream().anyMatch(item -> Double.valueOf(item.getNotSentNum()).compareTo(0.0) > 0);
            if (any) {
                return 2;
            } else {
                return 3;
            }
        }

    }


    @Override
    public FcOrder getOrderDetailById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Map<String, Object> getOrderDetail(Long id) {
        Map<String, Object> result = new HashMap<>();
        FcOrder fcOrder = baseMapper.selectById(id);
        Integer consignmentStatus = getConsignmentStatus(id);
        fcOrder.setConsignmentStatus(consignmentStatus);
        FcContract fcContract = getContact(fcOrder.getContractNumber());
        List<FcOrderProduct> products = fcOrderProductMapper.selectList(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getOrderId, id).orderByAsc(FcOrderProduct::getSapDetailNumber));
        //syncService.syncStore(products);
        List<FcOrderPayMilestone> milestones = fcOrderPayMilestoneMapper.selectList(new LambdaQueryWrapper<FcOrderPayMilestone>().eq(FcOrderPayMilestone::getOrderNumber, fcOrder.getOrderNumber()));
        //发货单明细
        LambdaQueryWrapper<FcOrderConsignment> wrapper = new LambdaQueryWrapper<FcOrderConsignment>()
                .eq(FcOrderConsignment::getOrderId, id);
        List<FcOrderConsignment> consignments = fcOrderConsignmentMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(consignments)) {
            consignments.forEach(item -> {
                item.setSaleType(fcOrder.getSaleType());
                item.setOrderTitle(fcOrder.getOrderTitle());
                item.setCustomer(fcOrder.getReciver());
                item.setAmount(item.getConsignmentAmount());
            });
        } else {
            consignments = new ArrayList<>();
        }
        //认领单
        List<PaymentClaimVO> paymentClaims = fcPaymentClaimMapper.selectPaymentClaimByOrder(id);
        if (CollectionUtils.isEmpty(paymentClaims)) {
            paymentClaims = Collections.EMPTY_LIST;
        }
        //接口日志
        result.put("order", fcOrder);
        result.put("contract", fcContract);
        result.put("products", products);
        result.put("milestones", milestones);
        result.put("paymentClaims", paymentClaims);
        result.put("consignments", consignments);

        return result;
    }

    @Override
    public List<FcOrder> getOrdersByCusId(String code) {
        LambdaQueryWrapper<FcOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(FcOrder::getId, FcOrder::getOrderTitle)
                .eq(FcOrder::getReciver, code)
                .or().eq(FcOrder::getSoldToParty, code)
                .or().eq(FcOrder::getBileeCd, code)
                .or().eq(FcOrder::getPayerCd, code);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public R<List<FcShippingPlanReportInfoVo>> getOrderByNoOrName(String orderNoOrName) {
        if (StringUtils.isEmpty(orderNoOrName)) {
            return R.fail("请输入订单编号或者订单名称");
        }
        LambdaQueryWrapper<FcOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrder::getOrderNumber, orderNoOrName).or()
                .eq(FcOrder::getOrderTitle, orderNoOrName);
        FcOrder fcOrder = baseMapper.selectVoOne(queryWrapper);
        if (Objects.isNull(fcOrder)) {
            return R.fail("未查询到对应的订单信息");
        }
        List<FcOrderProduct> products = fcOrderProductMapper.selectList(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getOrderId, fcOrder.getId()));
        List<FcShippingPlanReportInfoVo> voList = new ArrayList<>();
        products.forEach(fcOrderProduct -> {
            FcShippingPlanReportInfoVo vo = new FcShippingPlanReportInfoVo();
            vo.setOrderNumber(fcOrder.getOrderNumber());
            vo.setOrderTitle(fcOrder.getOrderTitle());
            vo.setSoldToParty(fcOrder.getSoldToParty());
            vo.setSapDetailNumber(fcOrderProduct.getSapDetailNumber());
            vo.setProductName(fcOrderProduct.getProductName());
            vo.setOrderProductId(fcOrderProduct.getId().toString());
            voList.add(vo);
        });
        return R.ok(voList);
    }

    @Override
    public R<List<FcShippingPlanReportInfoVo>> getOrderAndProduct(List<String> productIds) {
        if (productIds.isEmpty()) {
            return R.fail("productIds不能为空");
        }
        List<FcShippingPlanReportInfoVo> voList = new ArrayList<>();
        productIds.forEach(productId -> {
            FcOrderProduct product = fcOrderProductMapper.selectOne(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getId, Long.valueOf(productId)));
            Long orderId = product.getOrderId();
            LambdaQueryWrapper<FcOrder> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FcOrder::getId, orderId);
            FcOrder fcOrder = baseMapper.selectVoOne(wrapper);
            //封装vo
            FcShippingPlanReportInfoVo vo = new FcShippingPlanReportInfoVo();
            vo.setOrderNumber(fcOrder.getOrderNumber());
            vo.setOrderTitle(fcOrder.getOrderTitle());
            vo.setSoldToParty(fcOrder.getSoldToParty());
            vo.setSapDetailNumber(product.getSapDetailNumber());
            vo.setProductName(product.getProductName());
            vo.setProductNumber(product.getProductNumber());
            vo.setProductModel(product.getProductModel());
            //订单数量
            vo.setNum(product.getNum());
            //产品单价
            vo.setUnitPrice(product.getUnitPrice());
            //sap物料编码
            vo.setSapMaterialCode(product.getProductNumber());
            //计算产品总金额
            BigDecimal productNum = new BigDecimal(product.getNum());
            BigDecimal unitPrice = new BigDecimal(product.getNum());
            BigDecimal amount = productNum.multiply(unitPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
            vo.setTotalProductAmount(amount);
            //计算报关剩余数量 取值逻辑：订单产品明细的总数量-已报关数量
            LambdaQueryWrapper<FcShippingPlanFinancialAccounting> accountingWrapper = new LambdaQueryWrapper<>();
            accountingWrapper.eq(FcShippingPlanFinancialAccounting::getOrderProductId, productId);
            accountingWrapper.orderByAsc(FcShippingPlanFinancialAccounting::getId);
            List<FcShippingPlanFinancialAccounting> accountings = planFinancialAccountingMapper.selectList(accountingWrapper);
            if (accountings.isEmpty()) {
                //如果为null则取订单明细上的订单数量
                vo.setReportCustomsResidueNum(product.getNum());
                //剩余报关金额=产品总金额
                vo.setRemainingReportCustomsAmount(amount);
            } else {
                //不为null则统计出已报关总数量
                BigDecimal countReportCustomsNum = new BigDecimal(0);
                //已报关总金额
                BigDecimal countReportCustomsAmount = new BigDecimal(0);
                for (FcShippingPlanFinancialAccounting accounting : accountings) {
                    BigDecimal reportCustomsNum = new BigDecimal(accounting.getReportCustomsNum());
                    countReportCustomsNum = countReportCustomsNum.add(reportCustomsNum);
                    countReportCustomsAmount = countReportCustomsAmount.add(accounting.getCurrentReportCustomsAmount());
                }
                //产品总数量
                BigDecimal num = new BigDecimal(product.getNum());
                //剩余报关数量
                BigDecimal reportCustomsResidueNum = num.subtract(countReportCustomsNum);
                vo.setReportCustomsResidueNum(reportCustomsResidueNum.toString());
                //剩余报关金额
                vo.setRemainingReportCustomsAmount(amount.subtract(countReportCustomsAmount));
            }
            vo.setOrderProductId(product.getId().toString());
            voList.add(vo);
        });
        return R.ok(voList);
    }

    public static void main(String[] args) {
        BigDecimal countReportCustomsNum = new BigDecimal(0);
        BigDecimal reportCustomsNum = new BigDecimal("3.00");
        countReportCustomsNum.add(reportCustomsNum);
        System.out.println(countReportCustomsNum.toString());

    }

}
