package com.yy.train.member.controller;

import com.yy.train.common.context.LoginMemberContext;
import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.member.req.${Domain}QueryReq;
import com.yy.train.member.req.${Domain}SaveReq;
import com.yy.train.member.resp.${Domain}QueryResp;
import com.yy.train.member.service.${Domain}Service;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${do_main}")
public class ${Domain}Controller {
    @Resource
    private ${Domain}Service ${domain}Service;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody ${Domain}SaveReq ${domain}SaveReq) {
        ${domain}Service.save(${domain}SaveReq);
        return new CommonResp<>();
    }

    @GetMapping ("/query-list")
    public CommonResp<PageResp<${Domain}QueryResp>> queryList(@Valid ${Domain}QueryReq ${domain}QueryReq) {
        ${domain}QueryReq.setMemberId(LoginMemberContext.getId());
        PageResp<${Domain}QueryResp> pageResp = ${domain}Service.queryList(${domain}QueryReq);
        return new CommonResp<>(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        ${domain}Service.delete(id);
        return new CommonResp<>();
    }
}
