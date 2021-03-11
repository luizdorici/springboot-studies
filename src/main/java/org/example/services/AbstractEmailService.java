package org.example.services;

import org.example.domain.Client;
import org.example.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

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

    protected String htmlFromTemplateOrder(Order obj) {
        Context context = new Context();
        context.setVariable("order", obj);
        return templateEngine.process("email/orderConfirmation", context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Order obj) {
        try {
            MimeMessage mm = prepareMimeMessageFromOrder(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMessageFromOrder(Order obj) throws MessagingException {
        MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
        mmh.setTo(obj.getClient().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Confirmação do pedido: " + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplateOrder(obj), true);
        return mm;
    }

    @Override
    public void sendNewPasswordEmail(Client client, String newPass) {
        SimpleMailMessage smm = prepareNewPasswordEmail(client, newPass);
        sendEmail(smm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Client client, String newPass) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(client.getEmail());
        smm.setFrom(sender);
        smm.setSubject("Solicitação de nova senha");
        smm.setSentDate(new Date(System.currentTimeMillis()));
        smm.setText("Nova senha: " + newPass);
        return smm;
    }
}
