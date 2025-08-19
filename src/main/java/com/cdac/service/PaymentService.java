package com.cdac.service;

import java.util.List;
import com.cdac.entities.Payment;

public interface PaymentService {
    List<Payment> getAllPayments();
    String updatePaymentStatus(Long paymentId, boolean status);
    boolean getPaymentStatusForOrder(Long orderId);
}
