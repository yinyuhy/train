package com.yy.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yy.train.business.domain.DailyTrain;
import com.yy.train.business.domain.DailyTrainExample;
import com.yy.train.business.mapper.DailyTrainMapper;
import com.yy.train.business.req.DailyTrainQueryReq;
import com.yy.train.business.req.DailyTrainSaveReq;
import com.yy.train.business.resp.DailyTrainQueryResp;
import com.yy.train.common.resp.PageResp;
import com.yy.train.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyTrainService {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainService.class);

    @Resource
    private DailyTrainMapper dailyTrainMapper;

    public void save(DailyTrainSaveReq dailyTrainReq) {
        DateTime now = DateTime.now();
        DailyTrain dailyTrain = BeanUtil.copyProperties(dailyTrainReq, DailyTrain.class);
        if (ObjUtil.isNull(dailyTrainReq.getId())) {
            dailyTrain.setId(SnowUtil.getSnowflakeNextId());
            dailyTrain.setCreateTime(now);
            dailyTrain.setUpdateTime(now);
            dailyTrainMapper.insert(dailyTrain);
        }else {
            dailyTrain.setUpdateTime(now);
            dailyTrainMapper.updateByPrimaryKey(dailyTrain);
        }

    }

    public PageResp<DailyTrainQueryResp> queryList(DailyTrainQueryReq dailyTrainQueryReq) {
        DailyTrainExample dailyTrainExample = new DailyTrainExample();
        dailyTrainExample.setOrderByClause("date desc, code asc");
        DailyTrainExample.Criteria criteria = dailyTrainExample.createCriteria();
        if (ObjectUtil.isNotNull(dailyTrainQueryReq.getDate())) {
            criteria.andDateEqualTo(dailyTrainQueryReq.getDate());
        }
        if (ObjectUtil.isNotEmpty(dailyTrainQueryReq.getCode())) {
            criteria.andCodeEqualTo(dailyTrainQueryReq.getCode());
        }

        LOG.info("查询页码：{}", dailyTrainQueryReq.getPage());
        LOG.info("查询条数：{}", dailyTrainQueryReq.getSize());
        PageHelper.startPage(dailyTrainQueryReq.getPage(), dailyTrainQueryReq.getSize());
        List<DailyTrain> dailyTrains = dailyTrainMapper.selectByExample(dailyTrainExample);

        PageInfo<DailyTrain> pageInfo = new PageInfo<>(dailyTrains);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainQueryResp> dailyTrainQueryResps = BeanUtil.copyToList(dailyTrains, DailyTrainQueryResp.class);

        PageResp<DailyTrainQueryResp> pageResp = new PageResp<>();
        pageResp.setList(dailyTrainQueryResps);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainMapper.deleteByPrimaryKey(id);
    }
}
