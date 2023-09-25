package com.yy.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yy.train.common.resp.PageResp;
import com.yy.train.common.util.SnowUtil;
import com.yy.train.business.domain.TrainSeat;
import com.yy.train.business.domain.TrainSeatExample;
import com.yy.train.business.mapper.TrainSeatMapper;
import com.yy.train.business.req.TrainSeatQueryReq;
import com.yy.train.business.req.TrainSeatSaveReq;
import com.yy.train.business.resp.TrainSeatQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainSeatService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainSeatService.class);

    @Resource
    private TrainSeatMapper trainSeatMapper;

    public void save(TrainSeatSaveReq trainSeatReq) {
        DateTime now = DateTime.now();
        TrainSeat trainSeat = BeanUtil.copyProperties(trainSeatReq, TrainSeat.class);
        if (ObjUtil.isNull(trainSeatReq.getId())) {
            trainSeat.setId(SnowUtil.getSnowflakeNextId());
            trainSeat.setCreateTime(now);
            trainSeat.setUpdateTime(now);
            trainSeatMapper.insert(trainSeat);
        }else {
            trainSeat.setUpdateTime(now);
            trainSeatMapper.updateByPrimaryKey(trainSeat);
        }

    }

    public PageResp<TrainSeatQueryResp> queryList(TrainSeatQueryReq trainSeatQueryReq) {
        TrainSeatExample trainSeatExample = new TrainSeatExample();
        trainSeatExample.setOrderByClause("train_code asc, carriage_index asc, carriage_seat_index asc");
        TrainSeatExample.Criteria criteria = trainSeatExample.createCriteria();
        if (ObjectUtil.isNotEmpty(trainSeatQueryReq.getTrainCode())) {
            criteria.andTrainCodeEqualTo(trainSeatQueryReq.getTrainCode());
        }

        LOG.info("查询页码：{}", trainSeatQueryReq.getPage());
        LOG.info("查询条数：{}", trainSeatQueryReq.getSize());
        PageHelper.startPage(trainSeatQueryReq.getPage(), trainSeatQueryReq.getSize());
        List<TrainSeat> trainSeats = trainSeatMapper.selectByExample(trainSeatExample);

        PageInfo<TrainSeat> pageInfo = new PageInfo<>(trainSeats);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainSeatQueryResp> trainSeatQueryResps = BeanUtil.copyToList(trainSeats, TrainSeatQueryResp.class);

        PageResp<TrainSeatQueryResp> pageResp = new PageResp<>();
        pageResp.setList(trainSeatQueryResps);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        trainSeatMapper.deleteByPrimaryKey(id);
    }
}
