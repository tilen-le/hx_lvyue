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
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.FcPayment;
import com.hexing.system.domain.FcPaymentClaim;
import com.hexing.system.domain.form.PaymentForm;
import com.hexing.system.mapper.FcOrderMapper;
import com.hexing.system.mapper.FcPaymentMapper;
import com.hexing.system.service.IFcPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcPaymentServiceImpl implements IFcPaymentService {

    private final FcPaymentMapper baseMapper;

    private final FcOrderMapper orderMapper;

    public String generateInvoiceCode() {
        // 获取最新的订单ID
        Long latestId = baseMapper.selectMaxid();
        Long sequence = (latestId != null) ? latestId + 1 : 1;

        String formattedSequence = String.format("%05d", sequence); // 格式化为6位数字
        return "HK-" + formattedSequence;
    }

    @Override
    public int saveFcPayment(PaymentForm paymentForm) {
        LambdaQueryWrapper<FcPayment> queryWrapper = new LambdaQueryWrapper<>();
        Date budat = paymentForm.getBudat();
        Calendar ca = Calendar.getInstance();
        ca.setTime(budat);
        int year = ca.get(Calendar.YEAR);//年份数值
        queryWrapper.eq(FcPayment::getDocumentNumber, paymentForm.getBelnr())
                .eq(FcPayment::getCorporateName, paymentForm.getBukrs())
                .likeRight(FcPayment::getPostingDate, String.valueOf(year));
        Long count = baseMapper.selectCount(queryWrapper);
        if (Objects.equals(count, 0L)) {
            FcPayment fcPayment = new FcPayment();
            String number = generateInvoiceCode();
            fcPayment.setPaymentNumber(number);
            fcPayment.setCorporateName(paymentForm.getBukrs());
            //fcPayment.setPaymentNumber(paymentForm.getZuonr());
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
        return 0;
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
        Page<FcPayment> page = baseMapper.listFcPayment(pageQuery.build(), fcPayment);
        return TableDataInfo.build(page);
    }

    private Wrapper<FcPayment> buildQueryWrapper(FcPayment fcPayment) {
        QueryWrapper<FcPayment> wrapper = Wrappers.query();
        wrapper.eq("deleted", "0")
                .like(StringUtils.isNotEmpty(fcPayment.getCustomerName()), "customer_name", fcPayment.getCustomerName())
                .like(ObjectUtils.isNotNull(fcPayment.getPaymentNumber()), "payment_number", fcPayment.getPaymentNumber());

               // .eq(ObjectUtils.isNotNull(fcPayment.getClaimStatus()),"");
        return wrapper;
    }
}
