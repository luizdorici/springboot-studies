package org.example.resources;

import org.example.domain.Category;
import org.example.domain.Client;
import org.example.dto.CategoryDTO;
import org.example.dto.ClientDTO;
import org.example.dto.ClientNewDTO;
import org.example.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/clients")
public class ClientResource {

    private final ClientService service;

    @Autowired
    ClientResource(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<Client> list = service.findAll();
        List<ClientDTO> listDTO = list.stream().map(obj -> new ClientDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClientDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Page<Client> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<ClientDTO> pageDTO = list.map(obj -> new ClientDTO(obj));
        return ResponseEntity.ok().body(pageDTO);
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> findById(@PathVariable Integer id) {
        Client obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO objDto) {
        Client obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO objDto, @PathVariable Integer id) {
        Client obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
