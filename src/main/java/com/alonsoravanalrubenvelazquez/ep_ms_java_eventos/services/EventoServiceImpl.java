package com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.models.Comuna;
import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.models.Evento;
import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.repositories.ComunaRepository;
import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.repositories.EventoRepository;

@Service
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;
    private final ComunaRepository comunaRepository;

    public EventoServiceImpl(
        EventoRepository eventoRepository,
        ComunaRepository comunaRepository) {

        this.eventoRepository = eventoRepository;
        this.comunaRepository = comunaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Evento> findById(Long id) {
        return eventoRepository.findById(id);
    }

    @Override
    @Transactional
    public Evento save(Evento evento) {

        validarValoresNumericos(evento);

        if (evento.getComuna() == null ||
            evento.getComuna().getId() == null) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Debes indicar el ID de la comuna"
            );
        }

        Long comunaId = evento.getComuna().getId();

        Comuna comuna = comunaRepository.findById(comunaId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No existe la comuna con ID: " + comunaId
            ));

        evento.setComuna(comuna);

        return eventoRepository.save(evento);
    }

    @Override
    @Transactional
    public Evento update(Long id, Evento evento) {

        Evento existente = eventoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Evento no encontrado con ID: " + id
            ));

        validarValoresNumericos(evento);

        if (evento.getComuna() == null ||
            evento.getComuna().getId() == null) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Debes indicar el ID de la comuna"
            );
        }

        Long comunaId = evento.getComuna().getId();

        Comuna comuna = comunaRepository.findById(comunaId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Comuna no encontrada con ID: " + comunaId
            ));

        existente.setNombre(evento.getNombre());
        existente.setDescripcion(evento.getDescripcion());
        existente.setFecha(evento.getFecha());
        existente.setRecinto(evento.getRecinto());
        existente.setPrecio(evento.getPrecio());
        existente.setAforoMaximo(evento.getAforoMaximo());
        existente.setAforoDisponible(evento.getAforoDisponible());
        existente.setComuna(comuna);

        return eventoRepository.save(existente);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        eventoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return eventoRepository.existsById(id);
    }

    private void validarValoresNumericos(Evento evento) {

        if (evento == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Debes ingresar los datos del evento"
            );
        }

        if (evento.getPrecio() == null ||
            evento.getPrecio() < 0) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El precio no puede ser negativo ni nulo"
            );
        }

        if (evento.getAforoMaximo() == null ||
            evento.getAforoMaximo() <= 0) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El aforo máximo debe ser mayor que cero"
            );
        }

        if (evento.getAforoDisponible() == null ||
            evento.getAforoDisponible() < 0) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El aforo disponible no puede ser negativo ni nulo"
            );
        }

        if (evento.getAforoDisponible() >
            evento.getAforoMaximo()) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El aforo disponible no puede superar el aforo máximo"
            );
        }
    }

}
