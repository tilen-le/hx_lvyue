package com.hexing.system.service.impl;

import com.hexing.system.domain.FcPaymentClaimDetail;
import com.hexing.system.mapper.FcPaymentClaimDetailMapper;
import com.hexing.system.service.IFcPaymentClaimDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcPaymentClaimDetailServiceImpl implements IFcPaymentClaimDetailService {

    private final FcPaymentClaimDetailMapper baseMapper;

    @Override
    public int saveFcPaymentClaimDetail(FcPaymentClaimDetail fcPaymentClaimDetail) {
        return baseMapper.insert(fcPaymentClaimDetail);
    }

    @Override
    public int removeFcPaymentClaimDetail(Long id) {
        return 0;
    }

    @Override
    public List<FcPaymentClaimDetail> listClaimDetail(Long id) {
        return baseMapper.listDetail(id);
    }
}
