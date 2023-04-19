package com.hexing.workflow.service;

import com.hexing.common.core.domain.entity.SysUser;
import com.hexing.workflow.domain.bo.SysUserBo;
import com.hexing.workflow.domain.bo.SysUserMultiBo;

import java.util.List;
import java.util.Map;

/**
 * 
 * @description: 用户接口
 * @author: firerock_tech
 * @created: 2022-03-25
 */
public interface IUserService {

    /**
     * 通过用户ID查询用户集合
     *
     * @param userIds 用户ID
     * @return 用户对象信息
     */
    List<SysUser> selectListUserByIds(List<Long> userIds);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUser selectUserById(Long userId);

    /**
     * 分页查询工作流选人,角色，部门等
     * @param sysUserBo
     * @return
     */
    Map<String,Object> getWorkflowUserListByPage(SysUserBo sysUserBo);

    /**
     * 分页查询工作流选择加签人员
     * @param sysUserMultiBo
     * @return
     */
    Map<String, Object> getWorkflowAddMultiListByPage(SysUserMultiBo sysUserMultiBo);
}
