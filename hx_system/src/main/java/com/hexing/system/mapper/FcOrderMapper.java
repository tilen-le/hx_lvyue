package com.hexing.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcOrder;
import org.apache.ibatis.annotations.Param;

/**
 * @author firerock_tech
 */
public interface FcOrderMapper extends BaseMapperPlus<FcOrderMapper, FcOrder,FcOrder> {


    Page<FcOrder> selectPageOrderList(@Param("page") Page<FcOrder> page, @Param("order") FcOrder order);

}
