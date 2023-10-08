package com.yy.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yy.train.business.domain.ConfirmOrder;
import com.yy.train.business.domain.ConfirmOrderExample;
import com.yy.train.business.domain.DailyTrainTicket;
import com.yy.train.business.enums.ConfirmOrderStatusEnum;
import com.yy.train.business.mapper.ConfirmOrderMapper;
import com.yy.train.business.req.ConfirmOrderDoReq;
import com.yy.train.business.req.ConfirmOrderQueryReq;
import com.yy.train.business.resp.ConfirmOrderQueryResp;
import com.yy.train.common.context.LoginMemberContext;
import com.yy.train.common.resp.PageResp;
import com.yy.train.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConfirmOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderService.class);

    @Resource
    private ConfirmOrderMapper confirmOrderMapper;

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

//    public void save(ConfirmOrderDoReq confirmOrderReq) {
//        DateTime now = DateTime.now();
//        ConfirmOrder confirmOrder = BeanUtil.copyProperties(confirmOrderReq, ConfirmOrder.class);
//        if (ObjUtil.isNull(confirmOrderReq.getId())) {
//            confirmOrder.setId(SnowUtil.getSnowflakeNextId());
//            confirmOrder.setCreateTime(now);
//            confirmOrder.setUpdateTime(now);
//            confirmOrderMapper.insert(confirmOrder);
//        }else {
//            confirmOrder.setUpdateTime(now);
//            confirmOrderMapper.updateByPrimaryKey(confirmOrder);
//        }
//
//    }

    public PageResp<ConfirmOrderQueryResp> queryList(ConfirmOrderQueryReq confirmOrderQueryReq) {
        ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
        confirmOrderExample.setOrderByClause("id desc");
        ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();

        LOG.info("查询页码：{}", confirmOrderQueryReq.getPage());
        LOG.info("查询条数：{}", confirmOrderQueryReq.getSize());
        PageHelper.startPage(confirmOrderQueryReq.getPage(), confirmOrderQueryReq.getSize());
        List<ConfirmOrder> confirmOrders = confirmOrderMapper.selectByExample(confirmOrderExample);

        PageInfo<ConfirmOrder> pageInfo = new PageInfo<>(confirmOrders);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<ConfirmOrderQueryResp> confirmOrderQueryResps = BeanUtil.copyToList(confirmOrders, ConfirmOrderQueryResp.class);

        PageResp<ConfirmOrderQueryResp> pageResp = new PageResp<>();
        pageResp.setList(confirmOrderQueryResps);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        confirmOrderMapper.deleteByPrimaryKey(id);
    }

    public void doConfirm(ConfirmOrderDoReq confirmOrderReq) {
        //业务校验

        //保存确认订单表，状态初始
        Date date = confirmOrderReq.getDate();
        String trainCode = confirmOrderReq.getTrainCode();
        String start = confirmOrderReq.getStart();
        String end = confirmOrderReq.getEnd();

        DateTime now = DateTime.now();
        ConfirmOrder confirmOrder = new ConfirmOrder();
        confirmOrder.setId(SnowUtil.getSnowflakeNextId());
        confirmOrder.setMemberId(LoginMemberContext.getId());
        confirmOrder.setDate(date);
        confirmOrder.setTrainCode(trainCode);
        confirmOrder.setStart(start);
        confirmOrder.setEnd(end);
        confirmOrder.setDailyTrainTicketId(confirmOrderReq.getDailyTrainTicketId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
        confirmOrder.setCreateTime(now);
        confirmOrder.setUpdateTime(now);
        confirmOrder.setTickets(JSON.toJSONString(confirmOrderReq.getTickets()));

        confirmOrderMapper.insert(confirmOrder);

        //查出余票记录，得到真实库存
        DailyTrainTicket dailyTrainTicket = dailyTrainTicketService.selectByUnique(date, trainCode, start, end);
        LOG.info("查出余票记录: {}", dailyTrainTicket);
        //预扣减余票数量，判断余票是否充足

        //选座

            //一个车厢一个车厢获取座位数据

            //挑选符合条件的座位，如果这个车厢不满足，则进入下一个车厢

        //选中的座位后事务处理

            //座位表售卖情况
            //余票详情表修改余票
            //增加购票记录
            //更新订单状态成功
    }
}
