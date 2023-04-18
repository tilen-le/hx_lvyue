package com.hexing.workflow.service;

import com.hexing.workflow.domain.vo.ActDynamicFormVo;
import com.hexing.workflow.domain.bo.ActDynamicFormBo;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 流程单Service接口
 *
 * @author firerock_tech
 * @date 2022-08-11
 */
public interface IActDynamicFormService {

    /**
     * 查询流程单
     */
    ActDynamicFormVo queryById(Long id);

    /**
     * 查询流程单列表
     */
    TableDataInfo<ActDynamicFormVo> queryPageList(ActDynamicFormBo bo, PageQuery pageQuery);

    /**
     * 查询启用流程单列表
     */
    TableDataInfo<ActDynamicFormVo> queryPageEnableList(ActDynamicFormBo bo, PageQuery pageQuery);

    /**
     * 查询流程单列表
     */
    List<ActDynamicFormVo> queryList(ActDynamicFormBo bo);

    /**
     * 添加流程单
     */
    Boolean insertByBo(ActDynamicFormBo bo);

    /**
     * 修改流程单
     */
    Boolean updateByBo(ActDynamicFormBo bo);

    /**
     * 修改流程单
     */
    Boolean editForm(ActDynamicFormBo bo);

    /**
     * 校验并批量删除流程单信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
