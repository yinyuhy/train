package com.yy.train.business.controller.admin;

import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.business.req.DailyTrainCarriageQueryReq;
import com.yy.train.business.req.DailyTrainCarriageSaveReq;
import com.yy.train.business.resp.DailyTrainCarriageQueryResp;
import com.yy.train.business.service.DailyTrainCarriageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-carriage")
public class DailyTrainCarriageAdminController {
    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainCarriageSaveReq dailyTrainCarriageSaveReq) {
        dailyTrainCarriageService.save(dailyTrainCarriageSaveReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<DailyTrainCarriageQueryResp>> queryList(@Valid DailyTrainCarriageQueryReq dailyTrainCarriageQueryReq) {
        PageResp<DailyTrainCarriageQueryResp> pageResp = dailyTrainCarriageService.queryList(dailyTrainCarriageQueryReq);
        return new CommonResp<>(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        dailyTrainCarriageService.delete(id);
        return new CommonResp<>();
    }
}
