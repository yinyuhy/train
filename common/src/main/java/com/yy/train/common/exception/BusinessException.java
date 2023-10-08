package com.yy.train.common.exception;

public class BusinessException extends RuntimeException{
    private BusinessExceptionEnum e;

    public BusinessException(BusinessExceptionEnum businessExceptionEnum) {
        this.e = businessExceptionEnum;
    }

    public BusinessExceptionEnum getE() {
        return e;
    }

    public void setE(BusinessExceptionEnum businessExceptionEnum) {
        this.e = businessExceptionEnum;
    }

    /**
     * 不写入堆栈数据，提高性能
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
