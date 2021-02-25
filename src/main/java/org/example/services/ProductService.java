package org.example.services;

import org.example.domain.Category;
import org.example.domain.Product;
import org.example.repositories.CategoryRepository;
import org.example.repositories.ProductRepository;
import org.example.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product findById(Integer id) {
        Optional<Product> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Product.class.getName()));
    }

    public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Category> categories = categoryRepository.findAllById(ids);
        return repository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
    }
}
