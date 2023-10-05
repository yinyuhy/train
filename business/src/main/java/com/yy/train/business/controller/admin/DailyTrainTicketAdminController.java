package com.yy.train.business.controller.admin;

import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.business.req.DailyTrainTicketQueryReq;
import com.yy.train.business.req.DailyTrainTicketSaveReq;
import com.yy.train.business.resp.DailyTrainTicketQueryResp;
import com.yy.train.business.service.DailyTrainTicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-ticket")
public class DailyTrainTicketAdminController {
    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainTicketSaveReq dailyTrainTicketSaveReq) {
        dailyTrainTicketService.save(dailyTrainTicketSaveReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryList(@Valid DailyTrainTicketQueryReq dailyTrainTicketQueryReq) {
        PageResp<DailyTrainTicketQueryResp> pageResp = dailyTrainTicketService.queryList(dailyTrainTicketQueryReq);
        return new CommonResp<>(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        dailyTrainTicketService.delete(id);
        return new CommonResp<>();
    }
}
