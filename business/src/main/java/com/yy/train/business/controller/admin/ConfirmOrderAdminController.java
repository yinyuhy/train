package com.yy.train.business.controller.admin;

import com.yy.train.business.req.ConfirmOrderQueryReq;
import com.yy.train.business.resp.ConfirmOrderQueryResp;
import com.yy.train.business.service.ConfirmOrderService;
import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/confirm-order")
public class ConfirmOrderAdminController {
    @Resource
    private ConfirmOrderService confirmOrderService;
//
//    @PostMapping("/save")
//    public CommonResp<Object> save(@Valid @RequestBody ConfirmOrderDoReq confirmOrderSaveReq) {
//        confirmOrderService.save(confirmOrderSaveReq);
//        return new CommonResp<>();
//    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<ConfirmOrderQueryResp>> queryList(@Valid ConfirmOrderQueryReq confirmOrderQueryReq) {
        PageResp<ConfirmOrderQueryResp> pageResp = confirmOrderService.queryList(confirmOrderQueryReq);
        return new CommonResp<>(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        confirmOrderService.delete(id);
        return new CommonResp<>();
    }
}
