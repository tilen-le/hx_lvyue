package com.hexing.common.translation.impl;

import com.hexing.common.annotation.TranslationType;
import com.hexing.common.constant.TransConstant;
import com.hexing.common.core.service.DeptService;
import com.hexing.common.translation.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 部门翻译实现
 *
 * @author firerock_tech
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.DEPT_ID_TO_NAME)
public class DeptNameTranslationImpl implements TranslationInterface<String> {

    private final DeptService deptService;

    public String translation(Object key, String other) {
        return deptService.selectDeptNameByIds(key.toString());
    }
}
