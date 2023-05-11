package com.hexing.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcApproveConfig;
import com.hexing.system.domain.FcOrderInvoice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author firerock_tech
 */
public interface FcOrderInvoiceMapper extends BaseMapperPlus<FcOrderInvoiceMapper, FcOrderInvoice,FcOrderInvoice> {

    Page<FcOrderInvoice> listPageInvoice(@Param("page") Page<FcOrderInvoice> page, @Param("invoice") FcOrderInvoice invoice);
    @Select("SELECT MAX(id) FROM Fc_Order_Invoice")
    Long selectMaxid();
}
