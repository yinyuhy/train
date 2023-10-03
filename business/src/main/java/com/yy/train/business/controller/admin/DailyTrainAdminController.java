package com.yy.train.business.controller.admin;

import com.yy.train.business.req.DailyTrainQueryReq;
import com.yy.train.business.req.DailyTrainSaveReq;
import com.yy.train.business.resp.DailyTrainQueryResp;
import com.yy.train.business.service.DailyTrainService;
import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin/daily-train")
public class DailyTrainAdminController {
    @Resource
    private DailyTrainService dailyTrainService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainSaveReq dailyTrainSaveReq) {
        dailyTrainService.save(dailyTrainSaveReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<DailyTrainQueryResp>> queryList(@Valid DailyTrainQueryReq dailyTrainQueryReq) {
        PageResp<DailyTrainQueryResp> pageResp = dailyTrainService.queryList(dailyTrainQueryReq);
        return new CommonResp<>(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        dailyTrainService.delete(id);
        return new CommonResp<>();
    }

    @GetMapping("/gen-daily/{date}")
    public CommonResp<Object> genDaily(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        dailyTrainService.genDaily(date);
        return new CommonResp<>();
    }
}
