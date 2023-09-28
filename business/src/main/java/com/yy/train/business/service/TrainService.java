package com.yy.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yy.train.business.domain.Train;
import com.yy.train.business.domain.TrainExample;
import com.yy.train.business.mapper.TrainMapper;
import com.yy.train.business.req.TrainQueryReq;
import com.yy.train.business.req.TrainSaveReq;
import com.yy.train.business.resp.TrainQueryResp;
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
public class TrainService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainService.class);

    @Resource
    private TrainMapper trainMapper;

    public void save(TrainSaveReq trainReq) {
        DateTime now = DateTime.now();
        Train train = BeanUtil.copyProperties(trainReq, Train.class);
        if (ObjUtil.isNull(trainReq.getId())) {
            //判断车站唯一键是否存在
            Train carriageDB = selectByUnique(trainReq.getCode());
            if (ObjUtil.isNotEmpty(carriageDB)) {
                throw new BuisnessException(BuisnessExceptionEnum.BUSINESS_TRAIN_CODE_UNIQUE_ERROR);
            }
            train.setId(SnowUtil.getSnowflakeNextId());
            train.setCreateTime(now);
            train.setUpdateTime(now);
            trainMapper.insert(train);
        }else {
            train.setUpdateTime(now);
            trainMapper.updateByPrimaryKey(train);
        }

    }

    private Train selectByUnique(String code) {
        TrainExample trainExample = new TrainExample();
        trainExample.createCriteria().andCodeEqualTo(code);
        List<Train> trains = trainMapper.selectByExample(trainExample);
        if (CollUtil.isNotEmpty(trains)) {
            return trains.get(0);
        }

        return null;
    }

    public PageResp<TrainQueryResp> queryList(TrainQueryReq trainQueryReq) {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("id desc");
        TrainExample.Criteria criteria = trainExample.createCriteria();

        LOG.info("查询页码：{}", trainQueryReq.getPage());
        LOG.info("查询条数：{}", trainQueryReq.getSize());
        PageHelper.startPage(trainQueryReq.getPage(), trainQueryReq.getSize());
        List<Train> trains = trainMapper.selectByExample(trainExample);

        PageInfo<Train> pageInfo = new PageInfo<>(trains);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainQueryResp> trainQueryResps = BeanUtil.copyToList(trains, TrainQueryResp.class);

        PageResp<TrainQueryResp> pageResp = new PageResp<>();
        pageResp.setList(trainQueryResps);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        trainMapper.deleteByPrimaryKey(id);
    }

    public List<TrainQueryResp> queryAll() {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("code asc");
        TrainExample.Criteria criteria = trainExample.createCriteria();

        List<Train> trains = trainMapper.selectByExample(trainExample);

        return BeanUtil.copyToList(trains, TrainQueryResp.class);
    }
}
