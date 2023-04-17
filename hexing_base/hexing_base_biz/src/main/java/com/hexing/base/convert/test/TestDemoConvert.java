package com.hexing.base.convert.test;

import com.hexing.base.dal.dataobject.test.TestDemoDO;
import com.hexing.common.pojo.PageResult;
import com.hexing.base.controller.admin.test.vo.TestDemoCreateReqVO;
import com.hexing.base.controller.admin.test.vo.TestDemoExcelVO;
import com.hexing.base.controller.admin.test.vo.TestDemoRespVO;
import com.hexing.base.controller.admin.test.vo.TestDemoUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 字典类型 Convert
 *
 * @author firerock_tech
 */
@Mapper
public interface TestDemoConvert {

    TestDemoConvert INSTANCE = Mappers.getMapper(TestDemoConvert.class);

    TestDemoDO convert(TestDemoCreateReqVO bean);

    TestDemoDO convert(TestDemoUpdateReqVO bean);

    TestDemoRespVO convert(TestDemoDO bean);

    List<TestDemoRespVO> convertList(List<TestDemoDO> list);

    PageResult<TestDemoRespVO> convertPage(PageResult<TestDemoDO> page);

    List<TestDemoExcelVO> convertList02(List<TestDemoDO> list);

}
