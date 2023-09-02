package com.yy.train.member.controller;

import com.yy.train.common.resp.CommonResp;
import com.yy.train.member.req.MemberLoginReq;
import com.yy.train.member.req.MemberRegisterReq;
import com.yy.train.member.req.MemberSendCodeReq;
import com.yy.train.member.resp.MemberLoginResp;
import com.yy.train.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Integer> count() {
        int count = memberService.count();
        return new CommonResp<Integer>(count);
    }

    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq memberRegisterReq) {
        Long register = memberService.register(memberRegisterReq);
        return new CommonResp<Long>(register);
    }

    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid MemberSendCodeReq memberSendCodeReq) {
        memberService.sendCode(memberSendCodeReq);
        return new CommonResp<Long>();
    }

    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid MemberLoginReq memberLoginReq) {
        MemberLoginResp res = memberService.login(memberLoginReq);
        return new CommonResp<>(res);
    }
}
