package com.yy.train.${module}.controller;

import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.${module}.req.${Domain}QueryReq;
import com.yy.train.${module}.req.${Domain}SaveReq;
import com.yy.train.${module}.resp.${Domain}QueryResp;
import com.yy.train.${module}.service.${Domain}Service;
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
        PageResp<${Domain}QueryResp> pageResp = ${domain}Service.queryList(${domain}QueryReq);
        return new CommonResp<>(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id){
        ${domain}Service.delete(id);
        return new CommonResp<>();
    }
}
