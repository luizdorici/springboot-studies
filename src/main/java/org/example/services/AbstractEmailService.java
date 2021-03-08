package org.example.services;

import org.example.domain.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Order obj) {
        SimpleMailMessage smm = prepareSimpleMailMessageFromOrder(obj);
        sendEmail(smm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order obj) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(obj.getClient().getEmail());
        smm.setFrom(sender);
        smm.setSubject("Confirmação do pedido: " + obj.getId());
        smm.setSentDate(new Date(System.currentTimeMillis()));
        smm.setText(obj.toString());
        return smm;
    }


}
