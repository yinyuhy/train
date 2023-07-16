package com.yy.train.common.exception;

public enum BuisnessExceptionEnum {
    MOBILE_EX("电话号已经被注册");

    private String desc;

    BuisnessExceptionEnum(String e) {
        this.desc = e;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
