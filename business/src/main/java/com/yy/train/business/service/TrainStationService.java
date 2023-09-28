package com.yy.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yy.train.business.domain.TrainStation;
import com.yy.train.business.domain.TrainStationExample;
import com.yy.train.business.mapper.TrainStationMapper;
import com.yy.train.business.req.TrainStationQueryReq;
import com.yy.train.business.req.TrainStationSaveReq;
import com.yy.train.business.resp.TrainStationQueryResp;
import com.yy.train.common.exception.BuisnessException;
import com.yy.train.common.exception.BuisnessExceptionEnum;
import com.yy.train.common.resp.PageResp;
import com.yy.train.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainStationService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainStationService.class);

    @Resource
    private TrainStationMapper trainStationMapper;

    public void save(TrainStationSaveReq trainStationReq) {
        DateTime now = DateTime.now();
        TrainStation trainStation = BeanUtil.copyProperties(trainStationReq, TrainStation.class);
        if (ObjUtil.isNull(trainStationReq.getId())) {
            //判断车站唯一键是否存在
            TrainStation stationDB = selectByUnique(trainStationReq.getTrainCode(), trainStationReq.getIndex());
            if (ObjUtil.isNotEmpty(stationDB)) {
                throw new BuisnessException(BuisnessExceptionEnum.BUSINESS_TRAIN_STATION_INDEX_UNIQUE_ERROR);
            }
            //判断车站唯一键是否存在
            stationDB = selectByUnique(trainStationReq.getTrainCode(), trainStationReq.getName());
            if (ObjUtil.isNotEmpty(stationDB)) {
                throw new BuisnessException(BuisnessExceptionEnum.BUSINESS_TRAIN_STATION_NAME_UNIQUE_ERROR);
            }
            trainStation.setId(SnowUtil.getSnowflakeNextId());
            trainStation.setCreateTime(now);
            trainStation.setUpdateTime(now);
            trainStationMapper.insert(trainStation);
        }else {
            trainStation.setUpdateTime(now);
            trainStationMapper.updateByPrimaryKey(trainStation);
        }

    }

    private TrainStation selectByUnique(String trainCode, Integer index) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.createCriteria().andTrainCodeEqualTo(trainCode).andIndexEqualTo(index);
        List<TrainStation> trainStations = trainStationMapper.selectByExample(trainStationExample);
        if (CollUtil.isNotEmpty(trainStations)) {
            return trainStations.get(0);
        }

        return null;
    }

    private TrainStation selectByUnique(String trainCode, String name) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.createCriteria().andTrainCodeEqualTo(trainCode).andNameEqualTo(name);
        List<TrainStation> trainStations = trainStationMapper.selectByExample(trainStationExample);
        if (CollUtil.isNotEmpty(trainStations)) {
            return trainStations.get(0);
        }

        return null;
    }

    public PageResp<TrainStationQueryResp> queryList(TrainStationQueryReq trainStationQueryReq) {
        TrainStationExample trainStationExample = new TrainStationExample();
        trainStationExample.setOrderByClause("train_code asc, `index` asc");
        TrainStationExample.Criteria criteria = trainStationExample.createCriteria();
        if (ObjectUtil.isNotEmpty(trainStationQueryReq.getTrainCode())) {
            criteria.andTrainCodeEqualTo(trainStationQueryReq.getTrainCode());
        }

        LOG.info("查询页码：{}", trainStationQueryReq.getPage());
        LOG.info("查询条数：{}", trainStationQueryReq.getSize());
        PageHelper.startPage(trainStationQueryReq.getPage(), trainStationQueryReq.getSize());
        List<TrainStation> trainStations = trainStationMapper.selectByExample(trainStationExample);

        PageInfo<TrainStation> pageInfo = new PageInfo<>(trainStations);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainStationQueryResp> trainStationQueryResps = BeanUtil.copyToList(trainStations, TrainStationQueryResp.class);

        PageResp<TrainStationQueryResp> pageResp = new PageResp<>();
        pageResp.setList(trainStationQueryResps);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        trainStationMapper.deleteByPrimaryKey(id);
    }
}
