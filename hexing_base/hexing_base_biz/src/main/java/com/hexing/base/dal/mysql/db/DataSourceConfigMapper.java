package com.hexing.base.dal.mysql.db;

import com.hexing.database.mybatis.core.mapper.BaseMapperX;
import com.hexing.base.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author firerock_tech
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
