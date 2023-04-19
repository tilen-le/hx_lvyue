package com.hexing.resource.oss.exception;

/**
 * OSS异常类
 *
 * @author firerock_tech
 */
public class OssException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OssException(String msg) {
        super(msg);
    }

}
