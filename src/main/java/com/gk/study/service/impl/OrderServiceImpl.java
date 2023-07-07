package com.gk.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.entity.Order;
import com.gk.study.service.OrderService;
import com.gk.study.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    OrderMapper mapper;

    @Override
    public List<Order> getOrderList() {
        return mapper.getList();
    }

    @Override
    public void createOrder(Order order) {
        long ct = System.currentTimeMillis();
        order.setOrderTime(String.valueOf(ct));
        order.setOrderNumber(String.valueOf(ct));
        order.setStatus("1"); //订单生成默认状态为1，表示预定状态，家教确认后订单状态变为0，表示已成功预约
        mapper.insert(order);
    }

    @Override
    public void deleteOrder(String id) {
        mapper.deleteById(id);
    }

    @Override
    public void updateOrder(Order order) {
        mapper.updateById(order);
    }

    @Override
    public List<Order> getUserOrderList(String userId, String status) {
        return mapper.getUserOrderList(userId, status);
//        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userId);
//        if (StringUtils.isNotBlank(status)) {
//            queryWrapper.eq("status", status);
//        }
//        queryWrapper.orderBy(true, false, "order_time");
//        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<Order> teacherGetOrderListByStatus(String thingid, String status) {
        return mapper.teacherGetOrderListByStatus(thingid, status);
    }


}
