package com.yy.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.yy.train.common.exception.BuisnessException;
import com.yy.train.common.exception.BuisnessExceptionEnum;
import com.yy.train.common.util.SnowUtil;
import com.yy.train.member.domain.Member;
import com.yy.train.member.domain.MemberExample;
import com.yy.train.member.mapper.MemberMapper;
import com.yy.train.member.req.MemberRegisterReq;
import com.yy.train.member.req.MemberSendCodeReq;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

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
            throw new BuisnessException(BuisnessExceptionEnum.MOBILE_EX);
        }

        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }

    public void sendCode(MemberSendCodeReq memberSendCodeReq) {
        String mobile = memberSendCodeReq.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);

        //如果为空，则插入一条注册信息
        if (CollUtil.isEmpty(members)) {
            LOG.info("没有对应手机号，插入一条新数据");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        } else {
            LOG.info("手机号存在，不插入记录");
        }

        //生成验证码
//        String code = RandomUtil.randomString(4);
        String code = "8888";
        LOG.info("生成短信验证码:{}", code);

        //记录到短信验证码表中，手机号、发送时间、业务类型、验证码
        LOG.info("记录到短信验证码表中");

        //短信发送通道
        LOG.info("短信发送通道");
    }
}
