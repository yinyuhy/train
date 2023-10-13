package com.yy.train.member.controller.admin;

import com.yy.train.common.resp.CommonResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.member.req.TicketQueryReq;
import com.yy.train.member.resp.TicketQueryResp;
import com.yy.train.member.service.TicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {
    @Resource
    private TicketService ticketService;

    @GetMapping ("/query-list")
    public CommonResp<PageResp<TicketQueryResp>> queryList(@Valid TicketQueryReq ticketQueryReq) {
        PageResp<TicketQueryResp> pageResp = ticketService.queryList(ticketQueryReq);
        return new CommonResp<>(pageResp);
    }
}
