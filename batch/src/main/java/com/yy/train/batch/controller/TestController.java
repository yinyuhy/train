package com.yy.train.batch.controller;

import com.yy.train.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Resource
    BusinessFeign businessFeign;

    @GetMapping("/hello")
    public String hello() {
        String hello = businessFeign.hello();
        LOG.info(hello);
        return "Hello World! Batch! "  + hello;
    }
}


