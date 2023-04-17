package com.hexing.sms.core.client.impl.debug;

import com.hexing.common.exception.ErrorCode;
import com.hexing.common.exception.enums.GlobalErrorCodeConstants;
import com.hexing.sms.core.client.SmsCodeMapping;
import com.hexing.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.Objects;

/**
 * 钉钉的 SmsCodeMapping 实现类
 *
 * @author firerock_tech
 */
public class DebugDingTalkCodeMapping implements SmsCodeMapping {

    @Override
    public ErrorCode apply(String apiCode) {
        return Objects.equals(apiCode, "0") ? GlobalErrorCodeConstants.SUCCESS : SmsFrameworkErrorCodeConstants.SMS_UNKNOWN;
    }

}
