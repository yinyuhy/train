package com.yy.train.member.controller;

import com.yy.train.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class TestController {
    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public int count() {
        return memberService.count();
    }
}
