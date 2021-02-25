package org.example.services;

import org.example.domain.Order;
import org.example.domain.OrderItem;
import org.example.domain.PaymentWithTicket;
import org.example.domain.enums.PaymentState;
import org.example.repositories.OrderItemRepository;
import org.example.repositories.OrderRepository;
import org.example.repositories.PaymentRepository;
import org.example.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repository;

    @Autowired
    TicketService ticketService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clienteService;

    @Autowired
    OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order findById(Integer id) {
        Optional<Order> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Order.class.getName()));
    }

    @Transactional
    public Order insert(Order obj) {
        obj.setId(null);
        obj.setInstant(new Date());
        obj.setClient(clienteService.findById(obj.getClient().getId()));
        obj.getPayment().setStatus(PaymentState.PENDENTE);
        obj.getPayment().setOrder(obj);
        if(obj.getPayment()  instanceof PaymentWithTicket) {
            //no mundo real depende da geracao de boleto
            PaymentWithTicket payment = (PaymentWithTicket) obj.getPayment();
            ticketService.fillPaymentWithTicket(payment, obj.getInstant());
        }
        obj = repository.save(obj);
        paymentRepository.save(obj.getPayment());

        for(OrderItem item : obj.getItems()) {
            item.setDiscount(0.0);
            item.setProduct(productService.findById(item.getProduct().getId()));
            item.setPrice(item.getProduct().getPrice());
            item.setOrder(obj);
        }
        orderItemRepository.saveAll(obj.getItems());
        System.out.println(obj);
        return obj;
    }
}
