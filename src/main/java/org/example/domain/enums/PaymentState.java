package org.example.domain.enums;

public enum PaymentState {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Pago"),
    CANCELADO(3, "Cancelado");

    private int code;
    private String description;

    PaymentState(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentState toEnum(Integer code) {

        if(code == null) return null;

        for(PaymentState c : PaymentState.values()) {
            if(code.equals(c.getCode())) return c;
        }
        throw new IllegalArgumentException("Invalid id: " + code);
    }
}
