package org.example.services;

import org.example.domain.PaymentWithTicket;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TicketService {

    public void fillPaymentWithTicket(PaymentWithTicket payment, Date orderInstant) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(orderInstant);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        payment.setPaymentDate(cal.getTime());
    }
}
