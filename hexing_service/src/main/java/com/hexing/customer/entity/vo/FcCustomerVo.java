package com.hexing.customer.entity.vo;

import com.hexing.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author firerock_tech
 */
@Schema(description = "管理后台 - 客户分页 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FcCustomerVo extends PageParam {

    private String code;
    private String name;
    private String receiver;
    private String status;
}
