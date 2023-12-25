package com.application.rest.controllers;


import com.application.rest.controllers.DTO.MakerDTO;
import com.application.rest.entities.Maker;
import com.application.rest.service.iMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


@RestController
@RequestMapping("/api/maker")
public class MakerController {

    @Autowired
    private iMakerService makerService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Maker> makerOptional = makerService.findById(id);

        if(makerOptional.isPresent()){
            Maker maker = makerOptional.get();

            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .productList(maker.getProductList())
                    .build();

            return ResponseEntity.ok(makerDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll() {

        List<MakerDTO> makerList = makerService.findAll()
                .stream()
                .map(maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .name(maker.getName())
                        .productList(maker.getProductList())
                        .build()).toList();

        return ResponseEntity.ok(makerList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {

        if(makerDTO.getName() == null || makerDTO.getName().isEmpty()){
            return ResponseEntity.badRequest().body("The name is required");
        }

        Maker maker = Maker.builder()
                //.id(makerDTO.getId())
                .name(makerDTO.getName())
                //.productList(makerDTO.getProductList())
                .build();

        makerService.save(maker);

        return ResponseEntity.created(new URI("/api/maker/save")).build();

    }

    @PutMapping("/update/{id}")
    public ResponseEntity <?> update(@PathVariable Long id, @RequestBody MakerDTO makerDTO) throws URISyntaxException {

        Optional<Maker> makerOptional = makerService.findById(id);

        if(makerOptional.isPresent()){
            Maker maker = makerOptional.get(); //Recuperamos el entity.

            maker.setName(makerDTO.getName());
            //maker.setProductList(makerDTO.getProductList());

            makerService.save(maker);

            return ResponseEntity.ok("Registro Actualizado");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        Optional<Maker> makerOptional = makerService.findById(id);

        if(makerOptional.isPresent()){
            makerService.deleteById(id);
            return ResponseEntity.ok("Registro Eliminado");
        }

        return ResponseEntity.badRequest().build();
    }


}
