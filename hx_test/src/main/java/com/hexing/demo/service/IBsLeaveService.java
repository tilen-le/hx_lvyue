package com.hexing.demo.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.demo.domain.BsLeave;
import com.hexing.demo.domain.bo.BsLeaveBo;
import com.hexing.demo.domain.vo.BsLeaveVo;

import java.util.Collection;
import java.util.List;

/**
 * 请假业务Service接口
 *
 * @author firerock_tech
 * @date 2021-10-10
 */
public interface IBsLeaveService{
	/**
	 * 查询单个
	 * @return
	 */
	BsLeaveVo queryById(String id);

	/**
	 * 查询列表
	 */
    TableDataInfo<BsLeaveVo> queryPageList(BsLeaveBo bo, PageQuery pageQuery);

	/**
	 * 查询列表
	 */
	List<BsLeaveVo> queryList(BsLeaveBo bo);

	/**
	 * 根据新增业务对象插入请假业务
	 * @param bo 请假业务新增业务对象
	 * @return
	 */
    BsLeave insertByBo(BsLeaveBo bo);

	/**
	 * 根据编辑业务对象修改请假业务
	 * @param bo 请假业务编辑业务对象
	 * @return
	 */
    BsLeave updateByBo(BsLeaveBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<String> ids);
}
