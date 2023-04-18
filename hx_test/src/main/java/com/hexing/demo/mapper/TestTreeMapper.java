package com.hexing.demo.mapper;

import com.hexing.common.annotation.DataColumn;
import com.hexing.common.annotation.DataPermission;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.demo.domain.TestTree;
import com.hexing.demo.domain.vo.TestTreeVo;

/**
 * 测试树表Mapper接口
 *
 * @author firerock_tech
 *
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTreeMapper, TestTree, TestTreeVo> {

}
