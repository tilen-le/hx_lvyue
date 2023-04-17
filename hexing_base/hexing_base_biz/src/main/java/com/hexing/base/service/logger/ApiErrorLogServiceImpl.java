package com.hexing.base.service.logger;

import com.hexing.base.convert.logger.ApiErrorLogConvert;
import com.hexing.base.dal.dataobject.logger.ApiErrorLogDO;
import com.hexing.base.dal.mysql.logger.ApiErrorLogMapper;
import com.hexing.base.enums.logger.ApiErrorLogProcessStatusEnum;
import com.hexing.common.pojo.PageResult;
import com.hexing.base.api.logger.dto.ApiErrorLogCreateReqDTO;
import com.hexing.base.controller.admin.logger.vo.apierrorlog.ApiErrorLogExportReqVO;
import com.hexing.base.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.hexing.common.exception.util.ServiceExceptionUtil.exception;
import static com.hexing.base.enums.ErrorCodeConstants.API_ERROR_LOG_NOT_FOUND;
import static com.hexing.base.enums.ErrorCodeConstants.API_ERROR_LOG_PROCESSED;

/**
 * API 错误日志 Service 实现类
 *
 * @author firerock_tech
 */
@Service
@Validated
public class ApiErrorLogServiceImpl implements ApiErrorLogService {

    @Resource
    private ApiErrorLogMapper apiErrorLogMapper;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        ApiErrorLogDO apiErrorLog = ApiErrorLogConvert.INSTANCE.convert(createDTO)
                .setProcessStatus(ApiErrorLogProcessStatusEnum.INIT.getStatus());
        apiErrorLogMapper.insert(apiErrorLog);
    }

    @Override
    public PageResult<ApiErrorLogDO> getApiErrorLogPage(ApiErrorLogPageReqVO pageReqVO) {
        return apiErrorLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ApiErrorLogDO> getApiErrorLogList(ApiErrorLogExportReqVO exportReqVO) {
        return apiErrorLogMapper.selectList(exportReqVO);
    }

    @Override
    public void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId) {
        ApiErrorLogDO errorLog = apiErrorLogMapper.selectById(id);
        if (errorLog == null) {
            throw exception(API_ERROR_LOG_NOT_FOUND);
        }
        if (!ApiErrorLogProcessStatusEnum.INIT.getStatus().equals(errorLog.getProcessStatus())) {
            throw exception(API_ERROR_LOG_PROCESSED);
        }
        // 标记处理
        apiErrorLogMapper.updateById(ApiErrorLogDO.builder().id(id).processStatus(processStatus)
                .processUserId(processUserId).processTime(LocalDateTime.now()).build());
    }

}
