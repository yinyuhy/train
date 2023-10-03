package com.yy.train.business.controller.admin;

import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.business.req.DailyTrainStationQueryReq;
import com.yy.train.business.req.DailyTrainStationSaveReq;
import com.yy.train.business.resp.DailyTrainStationQueryResp;
import com.yy.train.business.service.DailyTrainStationService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-station")
public class DailyTrainStationAdminController {
    @Resource
    private DailyTrainStationService dailyTrainStationService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainStationSaveReq dailyTrainStationSaveReq) {
        dailyTrainStationService.save(dailyTrainStationSaveReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<DailyTrainStationQueryResp>> queryList(@Valid DailyTrainStationQueryReq dailyTrainStationQueryReq) {
        PageResp<DailyTrainStationQueryResp> pageResp = dailyTrainStationService.queryList(dailyTrainStationQueryReq);
        return new CommonResp<>(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        dailyTrainStationService.delete(id);
        return new CommonResp<>();
    }
}
