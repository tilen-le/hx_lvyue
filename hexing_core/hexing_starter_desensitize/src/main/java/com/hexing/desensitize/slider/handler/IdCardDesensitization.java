package com.hexing.desensitize.slider.handler;

import com.hexing.desensitize.slider.annotation.IdCardDesensitize;

/**
 * {@link IdCardDesensitize} 的脱敏处理器
 *
 * @author gaibu
 */
public class IdCardDesensitization extends AbstractSliderDesensitizationHandler<IdCardDesensitize> {
    @Override
    Integer getPrefixKeep(IdCardDesensitize annotation) {
        return annotation.prefixKeep();
    }

    @Override
    Integer getSuffixKeep(IdCardDesensitize annotation) {
        return annotation.suffixKeep();
    }

    @Override
    String getReplacer(IdCardDesensitize annotation) {
        return annotation.replacer();
    }
}
