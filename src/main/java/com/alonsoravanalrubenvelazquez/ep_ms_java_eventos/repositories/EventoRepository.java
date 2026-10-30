package com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
