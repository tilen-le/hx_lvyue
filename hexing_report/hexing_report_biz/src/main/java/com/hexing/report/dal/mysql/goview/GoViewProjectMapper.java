package com.hexing.report.dal.mysql.goview;

import com.hexing.common.pojo.PageParam;
import com.hexing.common.pojo.PageResult;
import com.hexing.database.mybatis.core.mapper.BaseMapperX;
import com.hexing.database.mybatis.core.query.LambdaQueryWrapperX;
import com.hexing.report.dal.dataobject.goview.GoViewProjectDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoViewProjectMapper extends BaseMapperX<GoViewProjectDO> {

    default PageResult<GoViewProjectDO> selectPage(PageParam reqVO, Long userId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<GoViewProjectDO>()
                .eq(GoViewProjectDO::getCreator, userId)
                .orderByDesc(GoViewProjectDO::getId));
    }

}
