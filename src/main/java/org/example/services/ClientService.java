package org.example.services;

import org.example.domain.Client;
import org.example.repositories.ClientRepository;
import org.example.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repository;

    @Autowired
    ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client findById(Integer id) {
        Optional<Client> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Client.class.getName()));
    }
}
