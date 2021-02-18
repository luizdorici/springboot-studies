package org.example;

import org.example.domain.*;
import org.example.domain.enums.ClientType;
import org.example.domain.enums.PaymentState;
import org.example.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
class TesteApplication implements CommandLineRunner {

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

    public static void main(String[] args) {
        SpringApplication.run(TesteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

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

        cat1.getProducts().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProducts().addAll(Arrays.asList(p2));

        p1.getCategories().addAll(Arrays.asList(cat1));
        p2.getCategories().addAll(Arrays.asList(cat1, cat2));
        p3.getCategories().addAll(Arrays.asList(cat1));

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        productRepository.saveAll(Arrays.asList(p1,p2,p3));

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
