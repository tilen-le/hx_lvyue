package com.hexing.system.dal.mysql.sms;

import com.hexing.common.pojo.PageResult;
import com.hexing.database.mybatis.core.mapper.BaseMapperX;
import com.hexing.database.mybatis.core.query.LambdaQueryWrapperX;
import com.hexing.system.controller.admin.sms.vo.log.SmsLogExportReqVO;
import com.hexing.system.controller.admin.sms.vo.log.SmsLogPageReqVO;
import com.hexing.system.entity.sms.SmsLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SmsLogMapper extends BaseMapperX<SmsLogDO> {

    default PageResult<SmsLogDO> selectPage(SmsLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SmsLogDO>()
                .eqIfPresent(SmsLogDO::getChannelId, reqVO.getChannelId())
                .eqIfPresent(SmsLogDO::getTemplateId, reqVO.getTemplateId())
                .likeIfPresent(SmsLogDO::getMobile, reqVO.getMobile())
                .eqIfPresent(SmsLogDO::getSendStatus, reqVO.getSendStatus())
                .betweenIfPresent(SmsLogDO::getSendTime, reqVO.getSendTime())
                .eqIfPresent(SmsLogDO::getReceiveStatus, reqVO.getReceiveStatus())
                .betweenIfPresent(SmsLogDO::getReceiveTime, reqVO.getReceiveTime())
                .orderByDesc(SmsLogDO::getId));
    }

    default List<SmsLogDO> selectList(SmsLogExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SmsLogDO>()
                .eqIfPresent(SmsLogDO::getChannelId, reqVO.getChannelId())
                .eqIfPresent(SmsLogDO::getTemplateId, reqVO.getTemplateId())
                .likeIfPresent(SmsLogDO::getMobile, reqVO.getMobile())
                .eqIfPresent(SmsLogDO::getSendStatus, reqVO.getSendStatus())
                .betweenIfPresent(SmsLogDO::getSendTime, reqVO.getSendTime())
                .eqIfPresent(SmsLogDO::getReceiveStatus, reqVO.getReceiveStatus())
                .betweenIfPresent(SmsLogDO::getReceiveTime, reqVO.getReceiveTime())
                .orderByDesc(SmsLogDO::getId));
    }

}
