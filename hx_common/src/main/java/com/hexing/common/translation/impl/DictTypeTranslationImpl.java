package com.hexing.common.translation.impl;

import com.hexing.common.annotation.TranslationType;
import com.hexing.common.constant.TransConstant;
import com.hexing.common.core.service.DictService;
import com.hexing.common.utils.StringUtils;
import com.hexing.common.translation.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 字典翻译实现
 *
 * @author firerock_tech
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.DICT_TYPE_TO_LABEL)
public class DictTypeTranslationImpl implements TranslationInterface<String> {

    private final DictService dictService;

    public String translation(Object key, String other) {
        if (key instanceof String && StringUtils.isNotBlank(other)) {
            return dictService.getDictLabel(other, key.toString());
        }
        return null;
    }
}
