package com.yy.train.common.exception;

public class BuisnessException extends RuntimeException{
    private BuisnessExceptionEnum e;

    public BuisnessException(BuisnessExceptionEnum buisnessExceptionEnum) {
        this.e = buisnessExceptionEnum;
    }

    public BuisnessExceptionEnum getE() {
        return e;
    }

    public void setE(BuisnessExceptionEnum buisnessExceptionEnum) {
        this.e = buisnessExceptionEnum;
    }

    /**
     * 不写入堆栈数据，提高性能
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
