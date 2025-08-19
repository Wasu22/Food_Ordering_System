//package com.cdac.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.cdac.dao.OrderDao;
//import com.cdac.entities.Order;
//
//@Component
//public class OrderScheduler {
//
//    @Autowired
//    private OrderDao orderDao;
//
//    @Autowired
//    private OrderService orderService;
//
//    @Scheduled(fixedRate = 60 * 60 * 1000) // every hour
//    public void autoUpdateOrdersEveryHour() {
//        List<Order> orders = orderDao.findAll();
//        for (Order order : orders) {
//            orderService.autoUpdateOrder(order);
//        }
//        System.out.println("Order auto-update triggered at " + LocalDateTime.now());
//    }
//}
//
