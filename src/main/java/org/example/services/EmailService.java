package org.example.services;

import org.example.domain.Order;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Order obj);

    void sendEmail(SimpleMailMessage msg);
}
