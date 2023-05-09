package com.hexing.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcPayment;
import com.hexing.system.domain.FcPaymentClaim;
import com.hexing.system.domain.form.PaymentForm;
import com.hexing.system.mapper.FcPaymentMapper;
import com.hexing.system.service.IFcPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcPaymentServiceImpl implements IFcPaymentService {

    private final FcPaymentMapper baseMapper;

    @Override
    public int saveFcPayment(PaymentForm paymentForm) {
        LambdaQueryWrapper<FcPayment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcPayment::getPaymentNumber, paymentForm.getZuonr());
        FcPayment fcPayment = baseMapper.selectOne(queryWrapper);
        if (ObjectUtils.isNotNull(fcPayment)) {
            fcPayment.setCorporateName(paymentForm.getBukrs());
            fcPayment.setPaymentNumber(paymentForm.getZuonr());
            fcPayment.setCustomerName(paymentForm.getName1());
            fcPayment.setCusCode(paymentForm.getKunnr());
            fcPayment.setDocumentNumber(paymentForm.getBelnr());
            fcPayment.setPaymentCurrency(paymentForm.getRwcur());
            fcPayment.setPaymentType(paymentForm.getBlart());
            fcPayment.setReceivedAmount(BigDecimal.valueOf(Math.abs(Double.parseDouble(paymentForm.getTsl()))));
            fcPayment.setPostingDate(paymentForm.getBudat());
            fcPayment.setAllocatedAmount(BigDecimal.valueOf(Math.abs(Double.parseDouble(paymentForm.getTslOa()))));
            fcPayment.setUndistributedAmount(BigDecimal.valueOf(Math.abs(Double.parseDouble(paymentForm.getTsl01()))));
            return baseMapper.updateById(fcPayment);
        } else {
            fcPayment = new FcPayment();
            fcPayment.setCorporateName(paymentForm.getBukrs());
            fcPayment.setPaymentNumber(paymentForm.getZuonr());
            fcPayment.setCustomerName(paymentForm.getName1());
            fcPayment.setCusCode(paymentForm.getKunnr());
            fcPayment.setDocumentNumber(paymentForm.getBelnr());
            fcPayment.setPaymentCurrency(paymentForm.getRwcur());
            fcPayment.setPaymentType(paymentForm.getBlart());
            fcPayment.setReceivedAmount(BigDecimal.valueOf(Math.abs(Double.parseDouble(paymentForm.getTsl()))));
            fcPayment.setPostingDate(paymentForm.getBudat());
            fcPayment.setAllocatedAmount(BigDecimal.valueOf(Math.abs(Double.parseDouble(paymentForm.getTslOa()))));
            fcPayment.setUndistributedAmount(BigDecimal.valueOf(Math.abs(Double.parseDouble(paymentForm.getTsl01()))));
            return baseMapper.insert(fcPayment);
        }
    }

    @Override
    public int claimFcPayment(FcPaymentClaim fcPaymentClaim) {
        return 0;
    }

    @Override
    public FcPayment getDetailById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public TableDataInfo<FcPayment> listFcPayment(FcPayment fcPayment, PageQuery pageQuery) {
        Page<FcPayment> page = baseMapper.selectPage(pageQuery.build(), buildQueryWrapper(fcPayment));
        return TableDataInfo.build(page);
    }

    private Wrapper<FcPayment> buildQueryWrapper(FcPayment fcPayment) {
        QueryWrapper<FcPayment> wrapper = Wrappers.query();
        wrapper.eq("deleted", "0")
                .like(StringUtils.isNotEmpty(fcPayment.getCustomerName()), "customer_name", fcPayment.getCustomerName())
                .eq(ObjectUtils.isNotNull(fcPayment.getPaymentNumber()), "payment_number", fcPayment.getPaymentNumber());
        return wrapper;
    }
}
