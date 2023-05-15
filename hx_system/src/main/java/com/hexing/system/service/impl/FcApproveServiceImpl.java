package com.hexing.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hexing.system.domain.FcApprove;
import com.hexing.system.mapper.FcApproveMapper;
import com.hexing.system.service.IFcApproveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author firerock_tech
 */
@Service
public class FcApproveServiceImpl implements IFcApproveService {
    @Resource
    private FcApproveMapper baseMapper;
    @Override
    public int saveFcApprove(FcApprove fcApprove) {
        return baseMapper.insert(fcApprove);
    }

    @Override
    public int updateFcApprove(FcApprove fcApprove) {
        return baseMapper.updateById(fcApprove);
    }

    @Override
    public FcApprove getFcApprove(Long mainId,Integer type) {
            return baseMapper.selectOne(new LambdaQueryWrapper<FcApprove>()
                    .eq(FcApprove::getMainId, mainId)
                    .eq(FcApprove::getType, type));

    }


}
