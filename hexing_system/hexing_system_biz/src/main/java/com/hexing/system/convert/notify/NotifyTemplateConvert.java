package com.hexing.system.convert.notify;

import java.util.*;

import com.hexing.common.pojo.PageResult;

import com.hexing.system.controller.admin.notify.vo.template.NotifyTemplateCreateReqVO;
import com.hexing.system.controller.admin.notify.vo.template.NotifyTemplateRespVO;
import com.hexing.system.controller.admin.notify.vo.template.NotifyTemplateUpdateReqVO;
import com.hexing.system.entity.notify.NotifyTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 站内信模版 Convert
 *
 * @author xrcoder
 */
@Mapper
public interface NotifyTemplateConvert {

    NotifyTemplateConvert INSTANCE = Mappers.getMapper(NotifyTemplateConvert.class);

    NotifyTemplateDO convert(NotifyTemplateCreateReqVO bean);

    NotifyTemplateDO convert(NotifyTemplateUpdateReqVO bean);

    NotifyTemplateRespVO convert(NotifyTemplateDO bean);

    List<NotifyTemplateRespVO> convertList(List<NotifyTemplateDO> list);

    PageResult<NotifyTemplateRespVO> convertPage(PageResult<NotifyTemplateDO> page);

}
