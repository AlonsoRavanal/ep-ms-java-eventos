package com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.services;

import java.util.List;
import java.util.Optional;

import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.models.Evento;

public interface EventoService {

    List<Evento> findAll();

    Optional<Evento> findById(Long id);

    Evento save(Evento evento);

    Evento update(Long id, Evento evento);

    void deleteById(Long id);

    boolean existsById(Long id);
}
