package org.example.resources;

import org.example.domain.Order;
import org.example.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/orders")
public class OrderResource {

    private final OrderService service;

    @Autowired
    OrderResource(OrderService service) {
        this.service = service;
    }


    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Integer id) {
        Order obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }
}
