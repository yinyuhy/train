package com.yy.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yy.train.common.context.LoginMemberContext;
import com.yy.train.common.resp.PageResp;
import com.yy.train.common.util.SnowUtil;
import com.yy.train.member.domain.${Domain};
import com.yy.train.member.domain.${Domain}Example;
import com.yy.train.member.mapper.${Domain}Mapper;
import com.yy.train.member.req.${Domain}QueryReq;
import com.yy.train.member.req.${Domain}SaveReq;
import com.yy.train.member.resp.${Domain}QueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ${Domain}Service {

    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Service.class);

    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    public void save(${Domain}SaveReq ${domain}Req) {
        DateTime now = DateTime.now();
        ${Domain} ${domain} = BeanUtil.copyProperties(${domain}Req, ${Domain}.class);
        if (ObjUtil.isNull(${domain}Req.getId())) {
            ${domain}.setId(SnowUtil.getSnowflakeNextId());
            ${domain}.setMemberId(LoginMemberContext.getId());
            ${domain}.setCreateTime(now);
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.insert(${domain});
        }else {
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.updateByPrimaryKey(${domain});
        }

    }

    public PageResp<${Domain}QueryResp> queryList(${Domain}QueryReq ${domain}QueryReq) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("id desc");
        ${Domain}Example.Criteria criteria = ${domain}Example.createCriteria();
        if (ObjUtil.isNotNull(${domain}QueryReq.getMemberId())) {
            criteria.andMemberIdEqualTo(${domain}QueryReq.getMemberId());
        }

        LOG.info("查询页码：{}", ${domain}QueryReq.getPage());
        LOG.info("查询条数：{}", ${domain}QueryReq.getSize());
        PageHelper.startPage(${domain}QueryReq.getPage(), ${domain}QueryReq.getSize());
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);

        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}s);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<${Domain}QueryResp> ${domain}QueryResps = BeanUtil.copyToList(${domain}s, ${Domain}QueryResp.class);

        PageResp<${Domain}QueryResp> pageResp = new PageResp<>();
        pageResp.setList(${domain}QueryResps);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
