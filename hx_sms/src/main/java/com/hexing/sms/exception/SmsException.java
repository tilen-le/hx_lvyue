package com.hexing.sms.exception;

/**
 * Sms异常类
 *
 * @author firerock_tech
 */
public class SmsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SmsException(String msg) {
        super(msg);
    }

}
