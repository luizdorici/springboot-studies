package org.example.services.validation;

import org.example.domain.Client;
import org.example.domain.enums.ClientType;
import org.example.dto.ClientNewDTO;
import org.example.repositories.ClientRepository;
import org.example.resources.exceptions.FieldMessage;
import org.example.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

    @Autowired
    private ClientRepository repository;

    @Override
    public void initialize(ClientInsert ann) {
    }

    @Override
    public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getType().equals(ClientType.PESSOAFISICA.getCode()) && !BR.isValidCPF(objDto.getCpfOrCnpj()))
            list.add(new FieldMessage("cpfOrCnpj", "Invalid CPF"));

        if(objDto.getType().equals(ClientType.PESSOAJURIDICA.getCode()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj()))
            list.add(new FieldMessage("cpfOrCnpj", "Invalid CNPJ"));

        Client aux = repository.findByEmail(objDto.getEmail());
        if(aux != null) {
            list.add(new FieldMessage("email", "Emails already in use"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
