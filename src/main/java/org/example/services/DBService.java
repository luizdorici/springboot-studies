package org.example.services;

import org.example.domain.*;
import org.example.domain.enums.ClientType;
import org.example.domain.enums.PaymentState;
import org.example.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public void instantiateTestDatabase() throws ParseException {

        Category cat1 = new Category(null, "Informática");
        Category cat2 = new Category(null, "Escrotório");
        Category cat3 = new Category(null, "Cama mesa e banho");
        Category cat4 = new Category(null, "Eletrônicos");
        Category cat5 = new Category(null, "Jardinagem");
        Category cat6 = new Category(null, "Decoração");
        Category cat7 = new Category(null, "Perfumaria");

        Product p1 = new Product(null, "Computador", 2000.00);
        Product p2 = new Product(null, "Impressora", 800.00);
        Product p3 = new Product(null, "Mouse", 80.00);
        Product p4 = new Product(null, "Mesa de escritório", 300.00);
        Product p5 = new Product(null, "Toalha", 50.00);
        Product p6 = new Product(null, "Colcha", 200.00);
        Product p7 = new Product(null, "TV true color", 1200.00);
        Product p8 = new Product(null, "Roçadeira", 800.00);
        Product p9 = new Product(null, "Abajour", 100.00);
        Product p10 = new Product(null, "Pendente", 180.00);
        Product p11 = new Product(null, "Shampoo", 90.00);


        cat1.getProducts().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProducts().addAll(Arrays.asList(p2,p4));
        cat3.getProducts().addAll(Arrays.asList(p5,p6));
        cat4.getProducts().addAll(Arrays.asList(p1,p2,p3,p7));
        cat5.getProducts().addAll(Arrays.asList(p8));
        cat6.getProducts().addAll(Arrays.asList(p9,p10));
        cat7.getProducts().addAll(Arrays.asList(p11));

        p1.getCategories().addAll(Arrays.asList(cat1,cat4));
        p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategories().addAll(Arrays.asList(cat1, cat4));
        p4.getCategories().addAll(Arrays.asList(cat2));
        p5.getCategories().addAll(Arrays.asList(cat3));
        p6.getCategories().addAll(Arrays.asList(cat3));
        p7.getCategories().addAll(Arrays.asList(cat4));
        p8.getCategories().addAll(Arrays.asList(cat5));
        p9.getCategories().addAll(Arrays.asList(cat6));
        p10.getCategories().addAll(Arrays.asList(cat6));
        p11.getCategories().addAll(Arrays.asList(cat7));

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        productRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11));

        State st1 = new State(null, "Minas Gerais");
        State st2 = new State(null, "São Paulo");

        City ct1 = new City(null, "Uberlândia", st1);
        City ct2 = new City(null, "São Paulo", st2);
        City ct3 = new City(null, "Campinas", st2);

        st1.getCities().addAll(Arrays.asList(ct1));
        st2.getCities().addAll(Arrays.asList(ct2,ct3));

        stateRepository.saveAll(Arrays.asList(st1,st2));
        cityRepository.saveAll(Arrays.asList(ct1,ct2,ct3));

        Client client = new Client(null, "Maria Silva", "maria@gmail.com", "11111111111", ClientType.PESSOAFISICA);

        client.getPhones().addAll(Arrays.asList("2352636", "25242563"));

        Address a1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "13569000", client, ct1);
        Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "13560000", client, ct2);

        client.getAddresses().addAll(Arrays.asList(a1,a2));

        clientRepository.saveAll(Arrays.asList(client));
        addressRepository.saveAll(Arrays.asList(a1,a2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Order o1 = new Order(null, sdf.parse("30/09/2017 10:32"), client, a1);
        Order o2 = new Order(null, sdf.parse("10/10/2017 19:35"), client, a2);

        Payment pay1 = new CreditCardPayment(null, PaymentState.QUITADO, o1, 6);
        o1.setPayment(pay1);

        Payment pay2 = new PaymentWithTicket(null, PaymentState.PENDENTE, o2, sdf.parse("20/10/2017 00:00"), null);
        o2.setPayment(pay2);

        client.getOrders().addAll(Arrays.asList(o1,o2));

        orderRepository.saveAll(Arrays.asList(o1,o2));
        paymentRepository.saveAll(Arrays.asList(pay1,pay2));

        OrderItem item1 = new OrderItem(o1, p1, 0.00, 1, 2000.00);
        OrderItem item2 = new OrderItem(o1, p3, 0.00, 2, 80.00);
        OrderItem item3 = new OrderItem(o2, p2, 100.00, 1, 800.00);

        o1.getItems().addAll(Arrays.asList(item1,item2));
        o2.getItems().addAll(Arrays.asList(item3));

        p1.getItems().addAll(Arrays.asList(item1));
        p2.getItems().addAll(Arrays.asList(item3));
        p3.getItems().addAll(Arrays.asList(item2));

        orderItemRepository.saveAll(Arrays.asList(item1,item2,item3));
    }
}
