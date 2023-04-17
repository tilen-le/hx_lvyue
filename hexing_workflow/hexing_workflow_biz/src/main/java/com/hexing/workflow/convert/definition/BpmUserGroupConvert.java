package com.hexing.workflow.convert.definition;

import java.util.*;

import com.hexing.workflow.controller.admin.definition.vo.group.BpmUserGroupCreateReqVO;
import com.hexing.workflow.controller.admin.definition.vo.group.BpmUserGroupRespVO;
import com.hexing.workflow.controller.admin.definition.vo.group.BpmUserGroupUpdateReqVO;
import com.hexing.workflow.dal.dataobject.definition.BpmUserGroupDO;
import com.hexing.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * 用户组 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface BpmUserGroupConvert {

    BpmUserGroupConvert INSTANCE = Mappers.getMapper(BpmUserGroupConvert.class);

    BpmUserGroupDO convert(BpmUserGroupCreateReqVO bean);

    BpmUserGroupDO convert(BpmUserGroupUpdateReqVO bean);

    BpmUserGroupRespVO convert(BpmUserGroupDO bean);

    List<BpmUserGroupRespVO> convertList(List<BpmUserGroupDO> list);

    PageResult<BpmUserGroupRespVO> convertPage(PageResult<BpmUserGroupDO> page);

    @Named("convertList2")
    List<BpmUserGroupRespVO> convertList2(List<BpmUserGroupDO> list);

}
