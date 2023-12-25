package com.application.rest.service;

import com.application.rest.entities.Maker;

import java.util.List;
import java.util.Optional;

public interface iMakerService {

    //Pegamos los mismos métodos que en el DAO
    List<Maker> findAll();
    Optional<Maker> findById(Long id);
    void save(Maker maker);
    void deleteById(Long id);

}
