package com.hexing.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.model.LoginUser;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.exception.ServiceException;
import com.hexing.common.helper.LoginHelper;
import com.hexing.common.utils.JsonUtils;
import com.hexing.system.domain.FcPayment;
import com.hexing.system.domain.FcPaymentClaim;
import com.hexing.system.domain.FcPaymentClaimDetail;
import com.hexing.system.mapper.FcPaymentClaimMapper;
import com.hexing.system.mapper.FcPaymentMapper;
import com.hexing.system.service.IFcPaymentClaimDetailService;
import com.hexing.system.service.IFcPaymentClaimService;
import com.hexing.system.utils.HttpKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private final HttpKit httpKit;


    @Override
    public int saveFcPaymentClaim(FcPaymentClaim fcPaymentClaim) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        LambdaQueryWrapper<FcPaymentClaim> queryWrapper = new LambdaQueryWrapper<>();
        int count = baseMapper.selectCount(queryWrapper).intValue();
        fcPaymentClaim.setClaimNumber("RL" + new DecimalFormat("0000000").format(count + 1));
        fcPaymentClaim.setClaimCurrency(loginUser.getUserId().toString());
        if (fcPaymentClaim.getDetails() == null) {
            throw new ServiceException("认领明细不能为空");
        }
        BigDecimal totalAmount = new BigDecimal(0);
        for (FcPaymentClaimDetail item : fcPaymentClaim.getDetails()) {
            totalAmount = totalAmount.add(item.getAmount());
        }
        fcPaymentClaim.setClaimAmount(totalAmount.toString());
        /**
         * 同步sap
         */
        Map<String, String> resultMap = submitClaimToSap(fcPaymentClaim, "");
        fcPaymentClaim.setSyncSapInfoTime(new Date());
        fcPaymentClaim.setSyncSapInfo(resultMap.get("params"));
        fcPaymentClaim.setStatus("1");
        int result = baseMapper.insert(fcPaymentClaim);
        if (result > 0) {
            fcPaymentClaim.getDetails().forEach(item -> {
                item.setClaimId(fcPaymentClaim.getId());
                fcPaymentClaimDetailService.saveFcPaymentClaimDetail(item);
            });
        }
        return result;
    }

    @Override
    public String updateFcPaymentClaim(FcPaymentClaim fcPaymentClaim) {
        LambdaQueryWrapper<FcPaymentClaim> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcPaymentClaim::getId, fcPaymentClaim.getId())
                .eq(FcPaymentClaim::getStatus, "1");
        FcPaymentClaim existClaim = baseMapper.selectOne(queryWrapper);
        if (ObjectUtils.isNull(existClaim)) {
            throw new ServiceException("该认领单已撤销");
        }
        /**
         * 同步sap
         */
        Map<String, String> resultMap = submitClaimToSap(existClaim, "X");
        existClaim.setCancelSapInfoTime(new Date());
        existClaim.setCancelSapInfo(resultMap.get("params"));
        existClaim.setStatus("0");
        int result = baseMapper.updateById(existClaim);

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
                .eq(ObjectUtils.isNotNull(fcPaymentClaim.getPaymentId()), "payment_id", fcPaymentClaim.getPaymentId());
        return wrapper;
    }


    private Map<String, String> submitClaimToSap(FcPaymentClaim fcPaymentClaim, String status) {
        Map<String, String> resultMap = new HashMap<>();
        LambdaQueryWrapper<FcPayment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcPayment::getId, fcPaymentClaim.getPaymentId());
        FcPayment fcPayment = fcPaymentMapper.selectOne(queryWrapper);
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_RLD");
        Map<String, Object> data = new HashMap<>();
        data.put("TSL_OA", fcPaymentClaim.getClaimAmount());
        data.put("ZNAME_OA", fcPaymentClaim.getClaimCurrency());
        data.put("BELNR", fcPayment.getDocumentNumber());
        data.put("ACTIVE", status);
        params.put("data", data);
        String result = httpKit.postData(params);
        log.error(result);
        resultMap.put("params", JsonUtils.toJsonString(params));
        resultMap.put("result", result);
        return resultMap;
    }


}
