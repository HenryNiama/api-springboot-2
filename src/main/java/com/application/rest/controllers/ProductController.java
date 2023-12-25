package com.application.rest.controllers;


import com.application.rest.controllers.DTO.ProductDTO;
import com.application.rest.entities.Product;
import com.application.rest.service.iProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private iProductService productService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);

        if(productOptional.isPresent()){
            Product product = productOptional.get();

            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getMaker())
                    .build();

            return ResponseEntity.ok(productDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll() {

        List<ProductDTO> productList = productService.findAll()
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build()).toList();

        return ResponseEntity.ok(productList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        if(productDTO.getName() == null || productDTO.getName().isEmpty()){
            return ResponseEntity.badRequest().body("The name is required");
        }

        if(productDTO.getPrice() == null){
            return ResponseEntity.badRequest().body("The price is required");
        }

        if(productDTO.getMaker() == null){
            return ResponseEntity.badRequest().body("The maker is required");
        }

        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .maker(productDTO.getMaker())
                .build();

        productService.save(product);

        return ResponseEntity.created(new URI("/api/product/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity <?> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws URISyntaxException {

        Optional<Product> productOptional = productService.findById(id);

        if(productOptional.isPresent()){
            Product product = productOptional.get();

            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setMaker(productDTO.getMaker());

            productService.save(product);

            return ResponseEntity.ok("Registro actualizado");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity <?> delete(@PathVariable Long id) {

        Optional<Product> productOptional = productService.findById(id);

        if(productOptional.isPresent()){
            productService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }

        return ResponseEntity.badRequest().build();
    }
}
