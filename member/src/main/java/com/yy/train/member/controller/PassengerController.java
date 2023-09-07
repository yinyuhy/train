package com.yy.train.member.controller;

import com.yy.train.common.context.LoginMemberContext;
import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.member.req.PassengerQueryReq;
import com.yy.train.member.req.PassengerSaveReq;
import com.yy.train.member.resp.PassengerQueryResp;
import com.yy.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq passengerSaveReq) {
        passengerService.save(passengerSaveReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq passengerQueryReq) {
        passengerQueryReq.setMemberId(LoginMemberContext.getId());
        PageResp<PassengerQueryResp> pageResp = passengerService.queryList(passengerQueryReq);
        return new CommonResp<>(pageResp);
    }
}
