package com.hexing.system.convert.notice;

import com.hexing.common.pojo.PageResult;
import com.hexing.system.controller.admin.notice.vo.NoticeCreateReqVO;
import com.hexing.system.controller.admin.notice.vo.NoticeRespVO;
import com.hexing.system.controller.admin.notice.vo.NoticeUpdateReqVO;
import com.hexing.system.entity.notice.NoticeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    PageResult<NoticeRespVO> convertPage(PageResult<NoticeDO> page);

    NoticeRespVO convert(NoticeDO bean);

    NoticeDO convert(NoticeUpdateReqVO bean);

    NoticeDO convert(NoticeCreateReqVO bean);

}
