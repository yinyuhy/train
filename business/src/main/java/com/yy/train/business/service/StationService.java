package com.yy.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yy.train.business.domain.Station;
import com.yy.train.business.domain.StationExample;
import com.yy.train.business.mapper.StationMapper;
import com.yy.train.business.req.StationQueryReq;
import com.yy.train.business.req.StationSaveReq;
import com.yy.train.business.resp.StationQueryResp;
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
public class StationService {

    private static final Logger LOG = LoggerFactory.getLogger(StationService.class);

    @Resource
    private StationMapper stationMapper;

    public void save(StationSaveReq stationReq) {
        DateTime now = DateTime.now();
        Station station = BeanUtil.copyProperties(stationReq, Station.class);
        if (ObjUtil.isNull(stationReq.getId())) {
            //判断车站唯一键是否存在
            Station stationDB = selectByUnique(stationReq.getName());
            if (ObjUtil.isNotEmpty(stationDB)) {
                throw new BuisnessException(BuisnessExceptionEnum.BUSINESS_STATION_NAME_UNIQUE_ERROR);
            }

            station.setId(SnowUtil.getSnowflakeNextId());
            station.setCreateTime(now);
            station.setUpdateTime(now);
            stationMapper.insert(station);
        }else {
            station.setUpdateTime(now);
            stationMapper.updateByPrimaryKey(station);
        }

    }

    private Station selectByUnique(String name) {
        StationExample stationExample = new StationExample();
        stationExample.createCriteria().andNameEqualTo(name);
        List<Station> stations = stationMapper.selectByExample(stationExample);
        if (CollUtil.isNotEmpty(stations)) {
            return stations.get(0);
        }

        return null;
    }

    public PageResp<StationQueryResp> queryList(StationQueryReq stationQueryReq) {
        StationExample stationExample = new StationExample();
        stationExample.setOrderByClause("id desc");
        StationExample.Criteria criteria = stationExample.createCriteria();

        LOG.info("查询页码：{}", stationQueryReq.getPage());
        LOG.info("查询条数：{}", stationQueryReq.getSize());
        PageHelper.startPage(stationQueryReq.getPage(), stationQueryReq.getSize());
        List<Station> stations = stationMapper.selectByExample(stationExample);

        PageInfo<Station> pageInfo = new PageInfo<>(stations);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<StationQueryResp> stationQueryResps = BeanUtil.copyToList(stations, StationQueryResp.class);

        PageResp<StationQueryResp> pageResp = new PageResp<>();
        pageResp.setList(stationQueryResps);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        stationMapper.deleteByPrimaryKey(id);
    }

    public List<StationQueryResp> queryAll() {
        StationExample stationExample = new StationExample();
        stationExample.setOrderByClause("name_pinyin asc");
        StationExample.Criteria criteria = stationExample.createCriteria();

        List<Station> stations = stationMapper.selectByExample(stationExample);

        return BeanUtil.copyToList(stations, StationQueryResp.class);
    }
}
