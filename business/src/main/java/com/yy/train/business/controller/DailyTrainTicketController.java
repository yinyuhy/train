package com.yy.train.business.controller;

import com.yy.train.business.req.DailyTrainTicketQueryReq;
import com.yy.train.business.resp.DailyTrainTicketQueryResp;
import com.yy.train.business.service.DailyTrainTicketService;
import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/daily-train-ticket")
public class DailyTrainTicketController {
    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @GetMapping ("/query-list")
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryList(@Valid DailyTrainTicketQueryReq dailyTrainTicketQueryReq) {
        PageResp<DailyTrainTicketQueryResp> pageResp = dailyTrainTicketService.queryList(dailyTrainTicketQueryReq);
        return new CommonResp<>(pageResp);
    }
}
