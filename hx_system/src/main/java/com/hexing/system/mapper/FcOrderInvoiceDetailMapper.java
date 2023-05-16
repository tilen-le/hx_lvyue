package com.hexing.system.mapper;

import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcOrderInvoiceDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author firerock_tech
 */
public interface FcOrderInvoiceDetailMapper extends BaseMapperPlus<FcOrderInvoiceDetailMapper,FcOrderInvoiceDetail,FcOrderInvoiceDetail> {
        @Select("SELECT * FROM fc_order_invoice_detail WHERE id = #{invoice_id}")
        FcOrderInvoiceDetail[] findAllFcOrderInvoiceDetail(Long id);

}
