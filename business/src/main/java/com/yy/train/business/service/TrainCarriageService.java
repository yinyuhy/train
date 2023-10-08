package com.yy.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yy.train.business.domain.TrainCarriage;
import com.yy.train.business.domain.TrainCarriageExample;
import com.yy.train.business.enums.SeatColEnum;
import com.yy.train.business.mapper.TrainCarriageMapper;
import com.yy.train.business.req.TrainCarriageQueryReq;
import com.yy.train.business.req.TrainCarriageSaveReq;
import com.yy.train.business.resp.TrainCarriageQueryResp;
import com.yy.train.common.exception.BusinessException;
import com.yy.train.common.exception.BusinessExceptionEnum;
import com.yy.train.common.resp.PageResp;
import com.yy.train.common.util.SnowUtil;
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

        //自动计算出列数和总座位数
        List<SeatColEnum> colsByType = SeatColEnum.getColsByType(trainCarriageReq.getSeatType());
        trainCarriageReq.setColCount(colsByType.size());
        trainCarriageReq.setSeatCount(trainCarriageReq.getRowCount() * trainCarriageReq.getColCount());

        TrainCarriage trainCarriage = BeanUtil.copyProperties(trainCarriageReq, TrainCarriage.class);
        if (ObjUtil.isNull(trainCarriageReq.getId())) {

            //判断车站唯一键是否存在
            TrainCarriage carriageDB = selectByUnique(trainCarriageReq.getTrainCode(), trainCarriageReq.getIndex());
            if (ObjUtil.isNotEmpty(carriageDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_CARRIAGE_INDEX_UNIQUE_ERROR);
            }
            trainCarriage.setId(SnowUtil.getSnowflakeNextId());
            trainCarriage.setCreateTime(now);
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.insert(trainCarriage);
        }else {
            trainCarriage.setUpdateTime(now);
            trainCarriageMapper.updateByPrimaryKey(trainCarriage);
        }

    }

    private TrainCarriage selectByUnique(String trainCode, Integer index) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.createCriteria().andTrainCodeEqualTo(trainCode).andIndexEqualTo(index);
        List<TrainCarriage> trainCarriages = trainCarriageMapper.selectByExample(trainCarriageExample);
        if (CollUtil.isNotEmpty(trainCarriages)) {
            return trainCarriages.get(0);
        }

        return null;
    }

    public PageResp<TrainCarriageQueryResp> queryList(TrainCarriageQueryReq trainCarriageQueryReq) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.setOrderByClause("train_code asc, `index` asc");
        TrainCarriageExample.Criteria criteria = trainCarriageExample.createCriteria();
        if (ObjectUtil.isNotEmpty(trainCarriageQueryReq.getTrainCode())) {
            criteria.andTrainCodeEqualTo(trainCarriageQueryReq.getTrainCode());
        }

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

    public List<TrainCarriage> selectByTrainCode(String trainCode) {
        TrainCarriageExample trainCarriageExample = new TrainCarriageExample();
        trainCarriageExample.setOrderByClause("`index` asc");
        TrainCarriageExample.Criteria criteria = trainCarriageExample.createCriteria();
        criteria.andTrainCodeEqualTo(trainCode);
        return trainCarriageMapper.selectByExample(trainCarriageExample);
    }
}
