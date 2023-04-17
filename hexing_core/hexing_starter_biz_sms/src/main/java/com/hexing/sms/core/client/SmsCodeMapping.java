package com.hexing.sms.core.client;

import com.hexing.common.exception.ErrorCode;
import com.hexing.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.function.Function;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @see SmsCommonResult
 * @see SmsFrameworkErrorCodeConstants
 *
 * @author firerock_tech
 */
public interface SmsCodeMapping extends Function<String, ErrorCode> {
}
