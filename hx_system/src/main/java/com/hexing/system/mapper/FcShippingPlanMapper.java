package com.hexing.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.FcShippingPlan;
import org.apache.ibatis.annotations.Param;

/**
 * @author firerock_tech
 */
public interface FcShippingPlanMapper extends BaseMapperPlus<FcShippingPlanMapper, FcShippingPlan, FcShippingPlan> {


    Page<FcShippingPlan> selectPageList(@Param("page") Page<FcShippingPlan> page, @Param("fcShippingPlan") FcShippingPlan fcShippingPlan);

}
