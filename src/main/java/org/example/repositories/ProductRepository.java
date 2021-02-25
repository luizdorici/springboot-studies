package org.example.repositories;

import org.example.domain.Category;
import org.example.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    //duas possibilidades de implementacao do mesmo metodo, uma utilizando a query com JPQL e outra utilizando
    //o padrão de nomes da JPA, ambos retornam o mesmo resultado
    //caso a query seja digitada acima do metodo ela sobrepoe o metodo existente, mesmo utilizando o padrao de nomes
//    @Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cat WHERE obj.name LIKE %:name% AND cat IN :categories")
//    Page<Product> search(@Param("name") String name,@Param("categories") List<Category> categories, Pageable pageRequest);
    Page<Product> findDistinctByNameContainingAndCategoriesIn(String name, List<Category> categories, Pageable pageRequest);
}
