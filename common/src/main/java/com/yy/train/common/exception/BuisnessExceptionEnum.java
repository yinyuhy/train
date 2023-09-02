package com.yy.train.common.exception;

public enum BuisnessExceptionEnum {
    MEMBER_MOBILE_EXIST("电话号已经被注册"),
    MEMBER_MOBILE_NOT_EXIST("请先完成发送短信验证码的功能"),
    MEMBER_MOBILE_CODE_ERROR("短信验证码输入错误");

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
