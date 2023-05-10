package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.exception.ServiceException;
import com.hexing.common.utils.JsonUtils;
import com.hexing.common.utils.StringUtils;
import com.hexing.system.domain.*;
import com.hexing.system.domain.form.*;
import com.hexing.system.domain.vo.PaymentClaimVO;
import com.hexing.system.mapper.*;
import com.hexing.system.service.IOrderService;
import com.hexing.system.utils.HttpKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
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
public class OrderServiceImpl implements IOrderService {

    private final FcOrderMapper baseMapper;
    private final FcContractMapper fcContractMapper;
    private final FcOrderProductMapper fcOrderProductMapper;
    private final FcOrderConsignmentMapper fcOrderConsignmentMapper;

    private final FcOrderPayMilestoneMapper fcOrderPayMilestoneMapper;
    private final FcPaymentClaimMapper fcPaymentClaimMapper;

    private final HttpKit httpKit;

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
        List<FcOrderPayMilestone> payMilestones = fcOrderPayMilestoneMapper.selectList();
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
        if (ObjectUtil.isNotNull(existOrder)) {
            existOrder.setContractNumber(orderForm.getVbelnRe());
            existOrder.setOrderNumber(orderForm.getVbeln());
            existOrder.setOrderTitle(orderForm.getVbelnT());
            existOrder.setCurrency(orderForm.getWaers());
            existOrder.setSapCreateTime(orderForm.getErdat());
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
            existOrder.setTaxRate(orderForm.getZsj());
            existOrder.setCustomerManagerNumber(orderForm.getKunnrEr());
            existOrder.setCustomerManager(orderForm.getKunnrErT());
            existOrder.setRequireDeliveryDate(orderForm.getVdatu());
            //销售组织
            existOrder.setSaleOrgCd(orderForm.getVkorg());
            existOrder.setSaleOrg(orderForm.getVtext());
            existOrder.setFactory(orderForm.getWerks());
            existOrder.setTaxRate(orderForm.getZsl() == null ? "0" : orderForm.getZsl());
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
            existOrder.setCustomerManagerNumber(orderForm.getKunnrEr());
            existOrder.setCustomerManager(orderForm.getKunnrErT());
            //收票方
            existOrder.setBileeCd(orderForm.getKunnrBp());
            existOrder.setBillee(orderForm.getKunnrBpT());
            existOrder.setTaxRate(orderForm.getZsl() == null ? "0" : orderForm.getZsl());
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
        StockForm stockForm = getStore(orderForm);
        FcOrderProduct fcOrderProduct = fcOrderProductMapper.selectOne(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getOrderId, existOrder.getId()));
        if (ObjectUtil.isNull(fcOrderProduct)) {
            fcOrderProduct = new FcOrderProduct();
            fcOrderProduct.setOrderId(existOrder.getId());
            fcOrderProduct.setNum(orderForm.getZmeng());
            fcOrderProduct.setProductNumber(orderForm.getMatnr());
            fcOrderProduct.setProductModel(orderForm.getZcpxh());
            fcOrderProduct.setProductName(orderForm.getMaktx());
            fcOrderProduct.setUnitPrice(orderForm.getNetprZpr0());
            fcOrderProduct.setInStorageNum(stockForm == null ? "0" : stockForm.getKzsl().toString());
            fcOrderProduct.setInTransitNum(stockForm == null ? "0" : stockForm.getZtsl().toString());
            fcOrderProduct.setNotSentNum(stockForm == null ? orderForm.getZmeng() : (Double.parseDouble(orderForm.getZmeng()) - stockForm.getFhsl()) + "");
            fcOrderProduct.setSapDetailNumber(orderForm.getPosnr());
            fcOrderProductMapper.insert(fcOrderProduct);
        } else {
            fcOrderProduct.setOrderId(existOrder.getId());
            fcOrderProduct.setNum(orderForm.getZmeng());
            fcOrderProduct.setProductNumber(orderForm.getMatnr());
            fcOrderProduct.setSapDetailNumber(orderForm.getPosnr());
            fcOrderProduct.setProductName(orderForm.getMaktx());
            fcOrderProduct.setInStorageNum(stockForm == null ? "0" : stockForm.getKzsl().toString());
            fcOrderProduct.setInTransitNum(stockForm == null ? "0" : stockForm.getZtsl().toString());
            fcOrderProduct.setNotSentNum(stockForm == null ? orderForm.getZmeng() : (Double.parseDouble(orderForm.getZmeng()) - stockForm.getFhsl()) + "");
            fcOrderProduct.setUnitPrice(orderForm.getNetprZpr0());
            fcOrderProductMapper.updateById(fcOrderProduct);
        }
    }

    public StockForm getStore(OrderForm orderForm) {
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_KCGX");
        Map<String, String> data = new HashMap<>();
        data.put("IV_MATNR", orderForm.getMatnr());
        data.put("IV_WERKS", orderForm.getWerks());
        data.put("IV_VBELN", orderForm.getVbeln());
        data.put("IV_POSNR", orderForm.getPosnr());
        params.put("data", data);
        String result = httpKit.getData(params);
        log.error(result);
        if (ObjectUtil.isNotNull(result)) {
            Type type = new TypeToken<ResultForm<StockForm>>() {
            }.getType();
            ResultForm<StockForm> customers = new Gson().fromJson(result, type);
            return customers.getData();
        }
        return null;
    }

    @Override
    public TableDataInfo<FcOrder> listOrders(FcOrder fcOrder, PageQuery pageQuery) {
        Page<FcOrder> page = baseMapper.selectPageOrderList(pageQuery.build(), fcOrder);
        page.getRecords().forEach(item -> {
            LambdaQueryWrapper<FcOrderProduct> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FcOrderProduct::getOrderId, item.getId());
            List<FcOrderProduct> products = fcOrderProductMapper.selectList(queryWrapper);
            if (products.size() > 0) {
                item.setHasChildren(true);
                item.setProducts(products);
                double sum = products.stream().mapToDouble(s -> Double.parseDouble(s.getNum())).sum();
                double sumInTransitNum = products.stream().mapToDouble(s -> Double.parseDouble(s.getInTransitNum())).sum();
                item.setSum(String.valueOf(sum));
                item.setSumInTransitNum(String.valueOf(sumInTransitNum));
                item.setConsignmentStatus(getConsignmentStatus(item.getId()));
                item.setStoreStatus(getStoreStatus(item.getId()));
            }
        });
        return TableDataInfo.build(page);
    }

    /**
     * 发货状态：
     * 1.全部产品行都完成发货，未发货数量都为0，
     * 2.发货状态为发货完成，部分发货是有产品行还有未发货数量，
     * 3.未发货是所有行项目已发都为0
     */
    private Integer getConsignmentStatus(Long orderId) {
        FcOrder fcOrder = this.baseMapper.selectById(orderId);
        List<FcOrderProduct> products = fcOrderProductMapper.selectList(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getOrderId, orderId));
        double orderSum = products.stream().mapToDouble(item-> Double.parseDouble(item.getNum())).sum();
        double sum = fcOrderConsignmentMapper.getConsignmentSum(orderId);
        if (ObjectUtil.isNull(sum) || sum == 0) {
            return 3;
        } else {
            double v = sum;
            int compare = Double.compare(v, orderSum);
            if (compare == 0) {
                return 1;
            } else if (compare < 0) {
                return 2;
            } else {
                throw new ServiceException("订单发货核对异常");
            }
        }
    }

    /**
     * 库存状态，部分备货，备货完成，未备货
     * 库存状态：全部产品行未发货=库存，备货状态为备货完成，
     * 部分备货是有产品行不等于库存，量，
     * 未备货是所有库存都为0且剩余发货不为0
     * @param orderId
     * @return
     */
    //todo
    private Integer getStoreStatus(Long orderId) {
        FcOrder fcOrder = this.baseMapper.selectById(orderId);
        return 1;
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
        List<FcOrderProduct> products = fcOrderProductMapper.selectList(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getOrderId, id));
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
        }
        //认领单
        List<PaymentClaimVO> paymentClaims = fcPaymentClaimMapper.selectPaymentClaimByOrder(id);
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
        queryWrapper.select(FcOrder::getId, FcOrder::getOrderTitle).eq(FcOrder::getReciver, code).or().eq(FcOrder::getSoldToParty, code);
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
        List<FcOrderProduct> products = fcOrderProductMapper.selectList(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getOrderId, fcOrder));
        List<FcShippingPlanReportInfoVo> voList = new ArrayList<>();
        products.forEach(fcOrderProduct -> {
            FcShippingPlanReportInfoVo vo = new FcShippingPlanReportInfoVo();
            vo.setOrderNumber(fcOrder.getOrderNumber());
            vo.setOrderTitle(fcOrder.getOrderTitle());
            vo.setSoldToParty(fcOrder.getSoldToParty());
            vo.setSapDetailNumber(fcOrderProduct.getSapDetailNumber());
            vo.setProductName(fcOrderProduct.getProductName());
            voList.add(vo);
        });
        return R.ok(voList);
    }

}
