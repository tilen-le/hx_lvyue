package com.hexing.workflow.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 
 * @description: 流程定义Mapper
 * @author: firerock_tech
 * @created: 2022-06-11
 */
public interface ProcessDefinitionMapper {

    /**
     * 修改流程定义挂起描述
     * @param definitionId 流程定义id
     * @param description 描述
     */
    @Update("update act_re_procdef set description_=#{description} where id_ = #{definitionId}")
    void updateDescriptionById(@Param("definitionId") String definitionId, @Param("description") String description);

}
