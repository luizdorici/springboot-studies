package org.example.resources;

import org.example.domain.Category;
import org.example.domain.Product;
import org.example.dto.CategoryDTO;
import org.example.dto.ProductDTO;
import org.example.resources.utils.URL;
import org.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/products")
public class ProductResource {

    private final ProductService service;

    @Autowired
    ProductResource(ProductService service) {
        this.service = service;
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        Product obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findPage(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "categories", defaultValue = "") String categories,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        String nameDecoded = URL.decodeParam(name);
        List<Integer> ids = URL.decodeList(categories);
        Page<Product> list = service.search(nameDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProductDTO> pageDTO = list.map(obj -> new ProductDTO(obj));
        return ResponseEntity.ok().body(pageDTO);
    }
}
