package com.yy.train.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hello")
    public String hello() {
        LOG.info("Hello World Business");
        return "Hello World! Business! " ;
    }
}
