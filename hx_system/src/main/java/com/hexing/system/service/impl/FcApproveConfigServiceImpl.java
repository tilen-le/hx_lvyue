package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.exception.ServiceException;
import com.hexing.common.utils.StringUtils;
import com.hexing.system.domain.FcApproveConfig;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.mapper.FcApproveConfigMapper;
import com.hexing.system.service.IFcApproveConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author firerock_tech
 */
@Service
@RequiredArgsConstructor
public class FcApproveConfigServiceImpl implements IFcApproveConfigService {
    @Resource
    private FcApproveConfigMapper baseMapper;
    @Override
    public int saveFcApproveConfig(FcApproveConfig fcApproveConfig) {
        return baseMapper.insert(fcApproveConfig);
    }
    @Override
    public int updateFcApproveConfig(FcApproveConfig fcApproveConfig) {
        LambdaQueryWrapper<FcApproveConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcApproveConfig::getId, fcApproveConfig.getId());
        if (baseMapper.selectCount(queryWrapper) == 0) {
            throw new ServiceException("该配置不存在或已删除");
        }
        return baseMapper.updateById(fcApproveConfig);
    }

    @Override
    public int removeFcApproveConfig(Long id) {
        LambdaQueryWrapper<FcApproveConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcApproveConfig::getId, id);
        FcApproveConfig fcApproveConfig = baseMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(fcApproveConfig)) {
            throw new ServiceException("该配置不存在或已删除");
        }
        fcApproveConfig.setDelFlag(1);
        return baseMapper.updateById(fcApproveConfig);
    }

    @Override
    public TableDataInfo<FcApproveConfig> listApproveConfig(FcApproveConfig fcApproveConfig, PageQuery pageQuery) {
        Page<FcApproveConfig> page = baseMapper.listPageConfig(pageQuery.build(),fcApproveConfig);
        return TableDataInfo.build(page);
    }

    public FcApproveConfig getFcApproveConfig(FcOrder order) {
        String marketingDepartment = order.getMarketingDepartment();
        if (StringUtils.isEmpty(marketingDepartment)){
            if ( "ZOR1".equals(order.getSaleTypeCd())){
                marketingDepartment = "国内";
            }else {
                marketingDepartment = "国外";
            }
        }
        FcApproveConfig approveConfig = baseMapper.selectOne(new LambdaQueryWrapper<FcApproveConfig>()
                .eq(FcApproveConfig::getFactory, order.getFactory())
                .eq(FcApproveConfig::getSaleDept, order.getMarketingDepartmentId(marketingDepartment)).last("limit 1"));
        return approveConfig;
    }

}
