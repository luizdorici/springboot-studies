package org.example.services;

import org.example.domain.Client;
import org.example.domain.Order;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Order obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Order obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Client client, String newPass);
}
