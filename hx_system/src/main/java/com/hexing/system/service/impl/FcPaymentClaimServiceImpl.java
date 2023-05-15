package com.hexing.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.model.LoginUser;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.exception.ServiceException;
import com.hexing.common.helper.LoginHelper;
import com.hexing.common.utils.JsonUtils;
import com.hexing.common.utils.StringUtils;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.FcPayment;
import com.hexing.system.domain.FcPaymentClaim;
import com.hexing.system.domain.FcPaymentClaimDetail;
import com.hexing.system.domain.form.ResultInfo;
import com.hexing.system.domain.form.SapResult;
import com.hexing.system.mapper.FcOrderMapper;
import com.hexing.system.mapper.FcPaymentClaimMapper;
import com.hexing.system.mapper.FcPaymentMapper;
import com.hexing.system.service.IFcPaymentClaimDetailService;
import com.hexing.system.service.IFcPaymentClaimService;
import com.hexing.system.utils.HttpKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcPaymentClaimServiceImpl implements IFcPaymentClaimService {

    private final FcPaymentClaimMapper baseMapper;
    private final IFcPaymentClaimDetailService fcPaymentClaimDetailService;
    private final FcPaymentMapper fcPaymentMapper;
    private final FcOrderMapper fcOrderMapper;

    private final HttpKit httpKit;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveFcPaymentClaim(FcPaymentClaim fcPaymentClaim) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        LambdaQueryWrapper<FcPaymentClaim> queryWrapper = new LambdaQueryWrapper<>();
        Long count = baseMapper.selectCount(queryWrapper);
        Long sequence = (count != null) ? count + 1 : 1;
        String formattedSequence = String.format("%05d", sequence);
        fcPaymentClaim.setClaimNumber("RL" + formattedSequence);
        fcPaymentClaim.setClaimCurrency(loginUser.getUserId().toString());
        if (fcPaymentClaim.getDetails() == null) {
            throw new ServiceException("认领明细不能为空");
        }
        BigDecimal totalAmount = new BigDecimal(0);
        for (FcPaymentClaimDetail item : fcPaymentClaim.getDetails()) {
            totalAmount = totalAmount.add(item.getAmount());
        }
        fcPaymentClaim.setClaimAmount(totalAmount.toString());
        fcPaymentClaim.setSyncSapTime(new Date());

        FcPayment fcPayment = fcPaymentMapper.selectById(fcPaymentClaim.getPaymentId());
        BigDecimal amount = fcPayment.getReceivedAmount();
        //固定死选择回款中的币种
        fcPaymentClaim.setPaymentCurrency(fcPayment.getPaymentCurrency());
        /**
         * 同步sap
         */
        Map<String, String> resultMap = submitClaimToSap(fcPaymentClaim, "");
        ResultInfo<SapResult> sapResult = isSuccess(resultMap.get("result"));
        log.error("result:{}",resultMap);

        assert sapResult != null;
        if ("S".equals(sapResult.getData().getType())) {
            fcPaymentClaim.setSyncSapInfo(sapResult.getData().getMessage());
            fcPaymentClaim.setSyncSapInfoTime(new Date());
            fcPaymentClaim.setStatus(1);
            fcPaymentClaim.setSyncSapStatus(1);
            fcPayment.setAllocatedAmount(fcPayment.getAllocatedAmount().add(new BigDecimal(fcPaymentClaim.getClaimAmount())));
            fcPayment.setUndistributedAmount(amount.subtract(fcPayment.getAllocatedAmount()));
            fcPaymentMapper.updateById(fcPayment);
        } else {
            //throw new ServiceException("同步sap信息异常"+sapResult.getData().getMessage());
            fcPaymentClaim.setSyncSapInfo(sapResult.getData().getMessage());
            fcPaymentClaim.setSyncSapInfoTime(new Date());
            fcPaymentClaim.setSyncSapStatus(0);
            fcPaymentClaim.setStatus(1);
        }
        int result = baseMapper.insert(fcPaymentClaim);
        if (result > 0) {
            fcPaymentClaim.getDetails().forEach(item -> {
                item.setClaimId(fcPaymentClaim.getId());
                fcPaymentClaimDetailService.saveFcPaymentClaimDetail(item);
            });
        }
        //syncPaymentAmount(fcPaymentClaim);
        return result;
    }

    /**
     * 修改回款单认领金额
     *
     * @param fcPaymentClaim
     */
    private void syncPaymentAmount(FcPaymentClaim fcPaymentClaim) {
        LambdaQueryWrapper<FcPayment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcPayment::getId, fcPaymentClaim.getPaymentId());
        FcPayment fcPayment = fcPaymentMapper.selectOne(queryWrapper);
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_KUHK");
        Map<String, Object> data = new HashMap<>();
        data.put("bukrs", fcPayment.getCorporateName());
        data.put("belnr", fcPayment.getDocumentNumber());
        data.put("budat", fcPayment.getPostingDate());
        params.put("data", data);
        log.error(JsonUtils.toJsonString(params));
        String result = httpKit.postData(params);
        log.error(result);
    }

    @Override
    public String updateFcPaymentClaim(FcPaymentClaim fcPaymentClaim) {
        LambdaQueryWrapper<FcPaymentClaim> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcPaymentClaim::getId, fcPaymentClaim.getId());
        FcPaymentClaim existClaim = baseMapper.selectOne(queryWrapper);
        if (ObjectUtils.isNull(existClaim) || existClaim.getStatus() == 0) {
            throw new ServiceException("该认领单已撤销");
        }
        fcPaymentClaim.setCancelSapTime(new Date());
        /**
         * 同步sap
         */
        FcPayment fcPayment = fcPaymentMapper.selectById(existClaim.getPaymentId());
        BigDecimal amount = fcPayment.getReceivedAmount();
        //固定死选择回款中的币种
        fcPaymentClaim.setPaymentCurrency(fcPayment.getPaymentCurrency());

        Map<String, String> resultMap = submitClaimToSap(existClaim, "X");
        ResultInfo<SapResult> sapResult = isSuccess(resultMap.get("result"));
        assert sapResult != null;
        log.error("result:{}",resultMap);
        if ("S".equals(sapResult.getData().getType())) {
            fcPaymentClaim.setCancelSapInfo(sapResult.getData().getMessage());
            fcPaymentClaim.setCancelSapInfoTime(new Date());
            fcPaymentClaim.setStatus(0);
            fcPaymentClaim.setSyncSapStatus(1);
            fcPayment.setAllocatedAmount(fcPayment.getAllocatedAmount().subtract(new BigDecimal(fcPaymentClaim.getClaimAmount())));
            fcPayment.setUndistributedAmount(amount.subtract(fcPayment.getAllocatedAmount()));
            fcPaymentMapper.updateById(fcPayment);
        } else {
            //throw new ServiceException("同步sap信息异常"+sapResult.getData().getMessage());
            fcPaymentClaim.setCancelSapInfo(sapResult.getData().getMessage());
            fcPaymentClaim.setCancelSapInfoTime(new Date());
            fcPaymentClaim.setStatus(1);
            fcPaymentClaim.setSyncSapStatus(0);
        }
        int result = baseMapper.updateById(fcPaymentClaim);
        return result > 0 ? "回款撤销成功" : resultMap.get("result");
    }

    @Override
    public TableDataInfo<FcPaymentClaim> listFcPaymentClaim(FcPaymentClaim claim, PageQuery pageQuery) {
        Page<FcPaymentClaim> page = baseMapper.selectPage(pageQuery.build(), buildQueryWrapper(claim));
        return TableDataInfo.build(page);
    }

    @Override
    public TableDataInfo<FcPaymentClaim> listClaimPage(FcPaymentClaim claim, PageQuery pageQuery) {
        Page<FcPaymentClaim> page = baseMapper.selectPageList(pageQuery.build(), claim);
        return TableDataInfo.build(page);
    }

    @Override
    public FcPaymentClaim getClaimDetail(String id) {
        return baseMapper.getDetailById(id);
    }

    private Wrapper<FcPaymentClaim> buildQueryWrapper(FcPaymentClaim fcPaymentClaim) {
        QueryWrapper<FcPaymentClaim> wrapper = Wrappers.query();
        wrapper.eq("deleted", "0")
                .eq(ObjectUtils.isNotNull(fcPaymentClaim.getPaymentId()), "payment_id", fcPaymentClaim.getPaymentId()
                        ).orderByDesc("update_time");
        return wrapper;
    }


    private Map<String, String> submitClaimToSap(FcPaymentClaim fcPaymentClaim, String status) {
        Map<String, String> resultMap = new HashMap<>();
        LambdaQueryWrapper<FcPayment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcPayment::getId, fcPaymentClaim.getPaymentId());
        FcPayment fcPayment = fcPaymentMapper.selectOne(queryWrapper);
        List<FcPaymentClaimDetail> details = fcPaymentClaim.getDetails();
        if (CollectionUtils.isEmpty(details)) {
            details = fcPaymentClaimDetailService.listClaimDetail(fcPaymentClaim.getId());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_RLD");
        List<Object> orders = new ArrayList<>(details.size());
        for (FcPaymentClaimDetail order : details) {
            LambdaQueryWrapper<FcOrder> orderQuery = new LambdaQueryWrapper<>();
            orderQuery.eq(FcOrder::getId, order.getOrderId());
            FcOrder fcOrder = fcOrderMapper.selectOne(orderQuery);
            if (ObjectUtils.isNull(fcOrder)) {
                throw new ServiceException("认领订单异常");
            }
            Map<String, Object> data = new HashMap<>();
            data.put("Bukrs", fcPayment.getCorporateName());
            data.put("KUNNR", fcPayment.getCusCode());
            data.put("KUNNR_OA1", fcPayment.getCusCode());
            data.put("BELNR", fcPayment.getDocumentNumber());
            data.put("NAME1", fcPayment.getCustomerName());
            data.put("NAME1_OA1", fcPayment.getCustomerName());
            data.put("TSL", fcPayment.getReceivedAmount());
            data.put("RWCUR", fcPayment.getPaymentCurrency());
            data.put("AUBEL", fcOrder.getOrderNumber());
            data.put("BSTKD", fcOrder.getOrderNumber());
            data.put("TSL_OA", fcPaymentClaim.getClaimAmount());
            data.put("ZUONR", fcPayment.getPaymentNumber());
            data.put("WXSXF_OA", "");
            data.put("SXFJE_OA", "");
            data.put("SXF_OA", order.getMilestonesId());
            data.put("ACTIVE", status);
            orders.add(data);
        }
        params.put("data", orders);
        log.error(JsonUtils.toJsonString(orders));
        String result = httpKit.postData(params);
        resultMap.put("params", JsonUtils.toJsonString(params));
        resultMap.put("result", result);
        return resultMap;
    }


    private ResultInfo<SapResult> isSuccess(String result) {
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        Type type = new TypeToken<ResultInfo<SapResult>>() {
        }.getType();
        return new Gson().fromJson(result, type);
    }
}
