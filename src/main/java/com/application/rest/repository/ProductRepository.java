package com.application.rest.repository;

import com.application.rest.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

    //Si tengo metodos distintos a los que provee CrudRepository, los puedo definir aqui:


    //Metodo 1: Solo con Query.
    @Query("SELECT p FROM Product p WHERE p.price >= ?1 AND p.price <= ?2") //Lenguaje JPQL
    List<Product> findProductByPriceInRange(BigDecimal min, BigDecimal max);

    //Metodo 2:
    //Query Methods que existen en JPA:
    List<Product> findProductByPriceBetween(BigDecimal min, BigDecimal max);

}
