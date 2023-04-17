package com.hexing.base.convert.file;

import com.hexing.base.dal.dataobject.file.FileDO;
import com.hexing.common.pojo.PageResult;
import com.hexing.base.controller.admin.file.vo.file.FileRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(FileDO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileDO> page);

}
