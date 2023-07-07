package com.gk.study.controller;

import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.Order;
import com.gk.study.permission.Access;
import com.gk.study.permission.AccessLevel;
import com.gk.study.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.jar.Pack200;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService service;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public APIResponse list(){
        List<Order> list =  service.getOrderList();

        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }

    // 用户订单
    @RequestMapping(value = "/userOrderList", method = RequestMethod.GET)
    public APIResponse userOrderList(String userId, String status){
        List<Order> list =  service.getUserOrderList(userId, status);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Transactional
    public APIResponse create(Order order) throws IOException {
        service.createOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "创建成功");
    }

    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public APIResponse delete(String ids){
        System.out.println("ids===" + ids);
        // 批量删除
        String[] arr = ids.split(",");
        for (String id : arr) {
            service.deleteOrder(id);
        }
        return new APIResponse(ResponeCode.SUCCESS, "删除成功");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public APIResponse update(Order order) throws IOException {
        service.updateOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "更新成功");
    }

    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @Transactional
    public APIResponse cancelOrder(Long id) throws IOException {
        Order order = new Order();
        order.setId(id);
        order.setStatus("7"); // 7=取消
        service.updateOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "取消成功");
    }

    @Access(level = AccessLevel.LOGIN)
    @RequestMapping(value = "/cancelUserOrder", method = RequestMethod.POST)
    @Transactional
    public APIResponse cancelUserOrder(Long id) throws IOException {
        Order order = new Order();
        order.setId(id);
        order.setStatus("7"); // 7=取消
        service.updateOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "取消成功");
    }

    //增加，老师确认订单
    @Access(level = AccessLevel.TEACHER)
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @Transactional
    public APIResponse confirmOrder(long id) throws IOException{
        Order order = new Order();
        order.setId(id);
        order.setStatus("0");
        service.updateOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "课程预约已确认");
    }
//
    //课程上完后，课程状态变为2，表示课程已上完,填写评价 由学生确认课程已上完？
    @Access(level = AccessLevel.LOGIN)
    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    @Transactional
    public APIResponse finishOrder(long id, String comment) throws IOException{
        Order order = new Order();
        order.setId(id);
        order.setStatus("2");
        order.setRemark(comment);
        service.updateOrder(order);
        return new APIResponse(ResponeCode.SUCCESS, "课程已完成!");
    }
//
//    //增加，老师根据订单状态查看订单=查看课程预约
    @Access(level = AccessLevel.TEACHER)
    @RequestMapping(value = "/browse", method = RequestMethod.POST)
    @Transactional
    public APIResponse browseOrder(String thingid, String status) throws IOException{
        List<Order> list =  service.teacherGetOrderListByStatus(thingid, status);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }
}
