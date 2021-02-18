package org.example.services;

import org.example.domain.Category;
import org.example.dto.CategoryDTO;
import org.example.repositories.CategoryRepository;
import org.example.services.exceptions.DataIntegrityException;
import org.example.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    @Autowired
    CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Category findById(Integer id) {
        Optional<Category> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Category.class.getName()));
    }

    public Category insert(Category obj) {
        obj.setId(null);
        return repository.save(obj);
    }

    public Category update(Category obj) {
        Category newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    public void delete(Integer id) {
        findById(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Not possible to exclude a category with products");
        }
    }

    public Category fromDTO(CategoryDTO objDto) {
        return new Category(objDto.getId(), objDto.getName());
    }

    private void updateData(Category newObj, Category obj) {
        newObj.setName(obj.getName());
    }
}
