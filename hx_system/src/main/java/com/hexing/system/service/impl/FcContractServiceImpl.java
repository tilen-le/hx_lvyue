package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hexing.system.domain.FcContract;
import com.hexing.system.domain.form.ContractForm;
import com.hexing.system.mapper.FcContractMapper;
import com.hexing.system.service.IFcContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcContractServiceImpl implements IFcContractService {

    private final FcContractMapper baseMapper;

    @Override
    public int saveContract(ContractForm contractForm) {
        LambdaQueryWrapper<FcContract> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcContract::getContractNumber, contractForm.getVbeln());
        FcContract existContract = baseMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(existContract)) {
            existContract = new FcContract();
            existContract.setContractName(contractForm.getVbelnT());
            existContract.setContractNumber(contractForm.getVbeln());
            return baseMapper.insert(existContract);
        } else {
            existContract.setContractName(contractForm.getVbelnT());
            existContract.setContractNumber(contractForm.getVbeln());
            return baseMapper.updateById(existContract);
        }
    }
}
