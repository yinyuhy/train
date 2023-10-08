package com.yy.train.common.exception;

public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("电话号已经被注册"),
    MEMBER_MOBILE_NOT_EXIST("请先完成发送短信验证码的功能"),
    MEMBER_MOBILE_CODE_ERROR("短信验证码输入错误"),

    BUSINESS_STATION_NAME_UNIQUE_ERROR("车站已存在"),
    BUSINESS_TRAIN_CODE_UNIQUE_ERROR("车次编号已存在"),
    BUSINESS_TRAIN_STATION_INDEX_UNIQUE_ERROR("同车次站序已存在"),
    BUSINESS_TRAIN_STATION_NAME_UNIQUE_ERROR("同车次站名已存在"),
    BUSINESS_TRAIN_CARRIAGE_INDEX_UNIQUE_ERROR("同车次厢号已存在"),
    CONFIRM_ORDER_TICKET_COUNT_ERROR("余票不足"),
    ;

    private String desc;

    BusinessExceptionEnum(String e) {
        this.desc = e;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
