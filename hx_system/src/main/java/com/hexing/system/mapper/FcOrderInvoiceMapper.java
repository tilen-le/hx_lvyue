package com.hexing.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcApproveConfig;
import com.hexing.system.domain.FcOrderInvoice;
import org.apache.ibatis.annotations.Param;

/**
 * @author firerock_tech
 */
public interface FcOrderInvoiceMapper extends BaseMapperPlus<FcOrderInvoiceMapper, FcOrderInvoice,FcOrderInvoice> {

    Page<FcOrderInvoice> listPageInvoice(@Param("page") Page<FcOrderInvoice> page, @Param("invoice") FcOrderInvoice invoice);

}
