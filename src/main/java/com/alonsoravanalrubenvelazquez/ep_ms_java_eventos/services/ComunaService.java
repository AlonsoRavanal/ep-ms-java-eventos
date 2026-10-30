package com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.services;

import java.util.List;
import java.util.Optional;

import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.models.Comuna;

public interface ComunaService {

    List<Comuna> findAll();

    Optional<Comuna> findById(Long id);

    Comuna save(Comuna comuna);

    Comuna update(Long id, Comuna comuna);

    void deleteById(Long id);

    boolean existsById(Long id);
}
