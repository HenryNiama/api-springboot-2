package com.application.rest.persistence;

import com.application.rest.entities.Product;

import java.math.BigDecimal;
import java.util.*;

public interface iProductDAO {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    List<Product> findByPriceInRange(BigDecimal min, BigDecimal max);
    void save(Product product);
    void deleteById(Long id);
}
