package com.hexing.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcApproveConfig;
import org.apache.ibatis.annotations.Param;

/**
 * @author firerock_tech
 */
public interface FcApproveConfigMapper extends BaseMapperPlus<FcApproveConfigMapper, FcApproveConfig,FcApproveConfig> {

    Page<FcApproveConfig> listPageConfig(@Param("page") Page<FcApproveConfig> page, @Param("config") FcApproveConfig order);
}
