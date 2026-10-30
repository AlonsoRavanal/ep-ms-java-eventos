package com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.models.Comuna;
import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.services.ComunaService;

@RestController
@RequestMapping("/api/comunas")
public class ComunaRestController {

    private final ComunaService comunaService;

    public ComunaRestController(ComunaService comunaService) {
        this.comunaService = comunaService;
    }

    @GetMapping
    public ResponseEntity<List<Comuna>> listar() {
        return ResponseEntity.ok(comunaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> obtener(@PathVariable Long id) {
        return comunaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comuna> crear(@RequestBody Comuna comuna) {
        Comuna creada = comunaService.save(comuna);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> actualizar(@PathVariable Long id, @RequestBody Comuna comuna) {
        if (!comunaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comunaService.update(id, comuna));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!comunaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        comunaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
