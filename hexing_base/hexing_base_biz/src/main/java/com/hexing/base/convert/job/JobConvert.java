package com.hexing.base.convert.job;

import com.hexing.base.dal.dataobject.job.JobDO;
import com.hexing.common.pojo.PageResult;
import com.hexing.base.controller.admin.job.vo.job.JobCreateReqVO;
import com.hexing.base.controller.admin.job.vo.job.JobExcelVO;
import com.hexing.base.controller.admin.job.vo.job.JobRespVO;
import com.hexing.base.controller.admin.job.vo.job.JobUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 定时任务 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface JobConvert {

    JobConvert INSTANCE = Mappers.getMapper(JobConvert.class);

    JobDO convert(JobCreateReqVO bean);

    JobDO convert(JobUpdateReqVO bean);

    JobRespVO convert(JobDO bean);

    List<JobRespVO> convertList(List<JobDO> list);

    PageResult<JobRespVO> convertPage(PageResult<JobDO> page);

    List<JobExcelVO> convertList02(List<JobDO> list);

}
