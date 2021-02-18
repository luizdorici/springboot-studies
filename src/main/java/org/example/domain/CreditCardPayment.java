package org.example.domain;

import org.example.domain.enums.PaymentState;

import javax.persistence.Entity;

@Entity
public class CreditCardPayment extends Payment{

    private static final long serialVersionUID = 1L;

    private Integer numberOfParcels;

    public CreditCardPayment() {
    }

    public CreditCardPayment(Integer id, PaymentState status, Order order, Integer numberOfParcels) {
        super(id, status, order);
        this.numberOfParcels = numberOfParcels;
    }

    public Integer getNumberOfParcels() {
        return numberOfParcels;
    }

    public void setNumberOfParcels(Integer numberOfParcels) {
        this.numberOfParcels = numberOfParcels;
    }
}
