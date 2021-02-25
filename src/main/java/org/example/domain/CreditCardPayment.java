package org.example.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.example.domain.enums.PaymentState;

import javax.persistence.Entity;

@Entity
@JsonTypeName("pagamentoComCartao")
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
