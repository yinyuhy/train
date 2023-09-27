package com.yy.train.business.controller.admin;

import com.yy.train.business.service.TrainSeatService;
import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.business.req.TrainQueryReq;
import com.yy.train.business.req.TrainSaveReq;
import com.yy.train.business.resp.TrainQueryResp;
import com.yy.train.business.service.TrainService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train")
public class TrainAdminController {
    @Resource
    private TrainService trainService;
    @Resource
    private TrainSeatService trainSeatService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TrainSaveReq trainSaveReq) {
        trainService.save(trainSaveReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<TrainQueryResp>> queryList(@Valid TrainQueryReq trainQueryReq) {
        PageResp<TrainQueryResp> pageResp = trainService.queryList(trainQueryReq);
        return new CommonResp<>(pageResp);
    }

    @GetMapping ("/query-all")
    public CommonResp<List<TrainQueryResp>> queryAll() {
        List<TrainQueryResp> pageResp = trainService.queryAll();
        return new CommonResp<>(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        trainService.delete(id);
        return new CommonResp<>();
    }

    @GetMapping("/gen-seat/{trainCode}")
    public CommonResp<Object> delete(@PathVariable String trainCode){
        trainSeatService.genTrainSeat(trainCode);
        return new CommonResp<>();
    }
}
