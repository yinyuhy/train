package com.yy.train.business.controller;

import com.yy.train.business.resp.TrainQueryResp;
import com.yy.train.business.service.TrainService;
import com.yy.train.common.resp.CommonResp;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {
    @Resource
    private TrainService trainService;

    @GetMapping ("/query-all")
    public CommonResp<List<TrainQueryResp>> queryAll() {
        List<TrainQueryResp> pageResp = trainService.queryAll();
        return new CommonResp<>(pageResp);
    }
}
