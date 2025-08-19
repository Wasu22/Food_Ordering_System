package com.cdac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.custom_exception.ApiException;
import com.cdac.dao.OrderDao;
import com.cdac.dao.PaymentDao;
import com.cdac.entities.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;
    private OrderDao orderDao;

    @Override
    public List<Payment> getAllPayments() {
        return paymentDao.findAll();
    }

    @Override
    public String updatePaymentStatus(Long paymentId, boolean status) {
        Payment payment = paymentDao.findById(paymentId)
                .orElseThrow(() -> new ApiException("Payment not found with ID: " + paymentId));

        payment.setStatus(status);
        paymentDao.save(payment);
        return "Payment status updated successfully.";
    }
    
   @Override
    public boolean getPaymentStatusForOrder(Long orderId) {
        return orderDao.findById(orderId)
            .orElseThrow(() -> new ApiException("Order not found with ID: " + orderId))
            .getMyPayment()
            .isStatus(); 
    }

}
