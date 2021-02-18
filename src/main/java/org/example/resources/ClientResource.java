package org.example.resources;

import org.example.domain.Client;
import org.example.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/clients")
public class ClientResource {

    private final ClientService service;

    @Autowired
    ClientResource(ClientService service) {
        this.service = service;
    }


    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> findById(@PathVariable Integer id) {
        Client obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }
}
