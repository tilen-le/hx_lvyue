package com.hexing.system.convert.tenant;

import com.hexing.common.pojo.PageResult;
import com.hexing.system.controller.admin.tenant.vo.packages.TenantPackageCreateReqVO;
import com.hexing.system.controller.admin.tenant.vo.packages.TenantPackageRespVO;
import com.hexing.system.controller.admin.tenant.vo.packages.TenantPackageSimpleRespVO;
import com.hexing.system.controller.admin.tenant.vo.packages.TenantPackageUpdateReqVO;
import com.hexing.system.entity.tenant.TenantPackageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户套餐 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface TenantPackageConvert {

    TenantPackageConvert INSTANCE = Mappers.getMapper(TenantPackageConvert.class);

    TenantPackageDO convert(TenantPackageCreateReqVO bean);

    TenantPackageDO convert(TenantPackageUpdateReqVO bean);

    TenantPackageRespVO convert(TenantPackageDO bean);

    List<TenantPackageRespVO> convertList(List<TenantPackageDO> list);

    PageResult<TenantPackageRespVO> convertPage(PageResult<TenantPackageDO> page);

    List<TenantPackageSimpleRespVO> convertList02(List<TenantPackageDO> list);

}
