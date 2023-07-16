package com.yy.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.yy.train.member.domain.Member;
import com.yy.train.member.domain.MemberExample;
import com.yy.train.member.mapper.MemberMapper;
import com.yy.train.member.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;

    public int count() {
       return Math.toIntExact(memberMapper.countByExample(null));
    }

    public Long register(MemberRegisterReq memberRegisterReq) {
        String mobile = memberRegisterReq.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);
        if (CollUtil.isNotEmpty(members)) {
            throw new RuntimeException("手机号已经被注册!");
        }

        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }
}
