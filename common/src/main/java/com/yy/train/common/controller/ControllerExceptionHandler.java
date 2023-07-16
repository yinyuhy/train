package com.yy.train.common.controller;

import com.yy.train.common.exception.BuisnessException;
import com.yy.train.common.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理，数据预处理
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 所有异常统一处理
     * 只要出现异常，都会进入这个方法
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp exceptionHandler(Exception e) {
        CommonResp commonResp = new CommonResp();
        LOG.error("系统异常:" + e);
        commonResp.setMessage("系统异常，请联系管理员");
        commonResp.setSuccess(false);
        return commonResp;
    }

    /**
     * 业务异常统一处理
     * 只要出现异常，都会进入这个方法
     */
    @ExceptionHandler(value = BuisnessException.class)
    @ResponseBody
    public CommonResp buisnessExceptionHandler(BuisnessException e) {
        CommonResp commonResp = new CommonResp();
        LOG.error("业务异常：{}" , e.getE().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getE().getDesc());
        return commonResp;
    }
}
