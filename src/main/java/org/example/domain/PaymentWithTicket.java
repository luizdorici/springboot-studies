package org.example.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.domain.enums.PaymentState;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class PaymentWithTicket extends Payment{

    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date paymentDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dueDate;

    public PaymentWithTicket() {
    }

    public PaymentWithTicket(Integer id, PaymentState status, Order order, Date paymentDate, Date dueDate) {
        super(id, status, order);
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
