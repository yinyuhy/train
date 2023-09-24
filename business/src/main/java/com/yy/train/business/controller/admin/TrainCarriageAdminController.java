package com.yy.train.business.controller.admin;

import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.business.req.TrainCarriageQueryReq;
import com.yy.train.business.req.TrainCarriageSaveReq;
import com.yy.train.business.resp.TrainCarriageQueryResp;
import com.yy.train.business.service.TrainCarriageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-carriage")
public class TrainCarriageAdminController {
    @Resource
    private TrainCarriageService trainCarriageService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TrainCarriageSaveReq trainCarriageSaveReq) {
        trainCarriageService.save(trainCarriageSaveReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<TrainCarriageQueryResp>> queryList(@Valid TrainCarriageQueryReq trainCarriageQueryReq) {
        PageResp<TrainCarriageQueryResp> pageResp = trainCarriageService.queryList(trainCarriageQueryReq);
        return new CommonResp<>(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        trainCarriageService.delete(id);
        return new CommonResp<>();
    }
}
