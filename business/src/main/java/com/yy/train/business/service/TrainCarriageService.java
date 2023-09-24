package com.yy.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yy.train.common.resp.PageResp;
import com.yy.train.common.util.SnowUtil;
import com.yy.train.business.domain.TrainCarriage;
import com.yy.train.business.domain.TrainCarriageExample;
import com.yy.train.business.mapper.TrainCarriageMapper;
import com.yy.train.business.req.TrainCarriageQueryReq;
import com.yy.train.business.req.TrainCarriageSaveReq;
import com.yy.train.business.resp.TrainCarriageQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainCarriageService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainCarriageService.class);

    @Resource
    private TrainCarriageMapper trainCarriageMapper;

    public void save(TrainCarriageSaveReq trainCarriageReq) {
        DateTime now = DateTime.now();
        TrainCarriage trainCarriage = BeanUtil.copyProperties(trainCarriageReq, TrainCarriage.class);
        if (ObjUtil.isNull(trainCarriageReq.getId())) {
            trainCarriage.setId(SnowUtil.getSnowflakeNextId());
            trainCarriage.setCreateTime(now);
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.insert(trainCarriage);
        }else {
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.updateByPrimaryKey(trainCarriage);
        }

    }

    public PageResp<TrainCarriageQueryResp> queryList(TrainCarriageQueryReq trainCarriageQueryReq) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.setOrderByClause("id desc");
        TrainCarriageExample.Criteria criteria = trainCarriageExample.createCriteria();

        LOG.info("查询页码：{}", trainCarriageQueryReq.getPage());
        LOG.info("查询条数：{}", trainCarriageQueryReq.getSize());
        PageHelper.startPage(trainCarriageQueryReq.getPage(), trainCarriageQueryReq.getSize());
        List<TrainCarriage> trainCarriages = trainCarriageMapper.selectByExample(trainCarriageExample);

        PageInfo<TrainCarriage> pageInfo = new PageInfo<>(trainCarriages);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainCarriageQueryResp> trainCarriageQueryResps = BeanUtil.copyToList(trainCarriages, TrainCarriageQueryResp.class);

        PageResp<TrainCarriageQueryResp> pageResp = new PageResp<>();
        pageResp.setList(trainCarriageQueryResps);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        trainCarriageMapper.deleteByPrimaryKey(id);
    }
}
