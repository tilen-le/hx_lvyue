package com.hexing.common.translation.impl;

import com.hexing.common.annotation.TranslationType;
import com.hexing.common.constant.TransConstant;
import com.hexing.common.core.service.OssService;
import com.hexing.common.translation.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * OSS翻译实现
 *
 * @author firerock_tech
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.OSS_ID_TO_URL)
public class OssUrlTranslationImpl implements TranslationInterface<String> {

    private final OssService ossService;

    public String translation(Object key, String other) {
        return ossService.selectUrlByIds(key.toString());
    }
}
