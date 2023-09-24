package com.yy.train.business.controller.admin;

import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.business.req.TrainQueryReq;
import com.yy.train.business.req.TrainSaveReq;
import com.yy.train.business.resp.TrainQueryResp;
import com.yy.train.business.service.TrainService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train")
public class TrainAdminController {
    @Resource
    private TrainService trainService;

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

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        trainService.delete(id);
        return new CommonResp<>();
    }
}
