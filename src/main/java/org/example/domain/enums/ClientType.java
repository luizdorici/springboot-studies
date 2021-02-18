package org.example.domain.enums;

public enum ClientType {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Juridica");

    private int code;
    private String description;

    ClientType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ClientType toEnum(Integer code) {

        if(code == null) return null;

        for(ClientType c : ClientType.values()) {
            if(code.equals(c.getCode())) return c;
        }
        throw new IllegalArgumentException("Invalid id: " + code);
    }
}
