package com.hexing.system.convert.auth;

import com.hexing.common.pojo.PageResult;
import com.hexing.system.controller.admin.oauth2.vo.client.OAuth2ClientCreateReqVO;
import com.hexing.system.controller.admin.oauth2.vo.client.OAuth2ClientRespVO;
import com.hexing.system.controller.admin.oauth2.vo.client.OAuth2ClientUpdateReqVO;
import com.hexing.system.entity.oauth2.OAuth2ClientDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * OAuth2 客户端 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface OAuth2ClientConvert {

    OAuth2ClientConvert INSTANCE = Mappers.getMapper(OAuth2ClientConvert.class);

    OAuth2ClientDO convert(OAuth2ClientCreateReqVO bean);

    OAuth2ClientDO convert(OAuth2ClientUpdateReqVO bean);

    OAuth2ClientRespVO convert(OAuth2ClientDO bean);

    List<OAuth2ClientRespVO> convertList(List<OAuth2ClientDO> list);

    PageResult<OAuth2ClientRespVO> convertPage(PageResult<OAuth2ClientDO> page);

}
