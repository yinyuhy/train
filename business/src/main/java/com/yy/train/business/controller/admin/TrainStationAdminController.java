package com.yy.train.business.controller.admin;

import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.business.req.TrainStationQueryReq;
import com.yy.train.business.req.TrainStationSaveReq;
import com.yy.train.business.resp.TrainStationQueryResp;
import com.yy.train.business.service.TrainStationService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-station")
public class TrainStationAdminController {
    @Resource
    private TrainStationService trainStationService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TrainStationSaveReq trainStationSaveReq) {
        trainStationService.save(trainStationSaveReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<TrainStationQueryResp>> queryList(@Valid TrainStationQueryReq trainStationQueryReq) {
        PageResp<TrainStationQueryResp> pageResp = trainStationService.queryList(trainStationQueryReq);
        return new CommonResp<>(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        trainStationService.delete(id);
        return new CommonResp<>();
    }
}
