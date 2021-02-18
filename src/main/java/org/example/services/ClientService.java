package org.example.services;

import org.example.domain.Address;
import org.example.domain.City;
import org.example.domain.Client;
import org.example.domain.enums.ClientType;
import org.example.dto.ClientDTO;
import org.example.dto.ClientNewDTO;
import org.example.repositories.AddressRepository;
import org.example.repositories.CityRepository;
import org.example.repositories.ClientRepository;
import org.example.services.exceptions.DataIntegrityException;
import org.example.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    //duas formas de fazer, criar as variveis nao final com autowired ou utilizar construtor
    private final ClientRepository repository;
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;

    @Autowired
    ClientService(ClientRepository repository, CityRepository cityRepository, AddressRepository addressRepository) {
        this.repository = repository;
        this.cityRepository = cityRepository;
        this.addressRepository = addressRepository;
    }


    public List<Client> findAll() {
        return repository.findAll();
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Client findById(Integer id) {
        Optional<Client> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Client.class.getName()));
    }

    @Transactional
    public Client insert(Client obj) {
        obj.setId(null);
        obj = repository.save(obj);
        addressRepository.saveAll(obj.getAddresses());
        return obj;
    }

    public Client update(Client obj) {
        Client newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    public void delete(Integer id) {
        findById(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Not possible to exclude a client with related entities");
        }
    }

    public Client fromDTO(ClientDTO objDto) {
        return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
    }

    public Client fromDTO(ClientNewDTO objDto) {

        Client client = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOrCnpj(), ClientType.toEnum(objDto.getType()));
        City city = new City(objDto.getCityId(), null, null);
        Address address = new Address(null, objDto.getStreet(), objDto.getNumber(), objDto.getComplement(), objDto.getDistrict(), objDto.getZipCode(), client, city);

        client.getAddresses().add(address);
        client.getPhones().add(objDto.getPhone1());

        if(objDto.getPhone2() != null) client.getPhones().add(objDto.getPhone2());
        if(objDto.getPhone3() != null) client.getPhones().add(objDto.getPhone3());
        return client;
    }

    private void updateData(Client newObj, Client obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }
}
