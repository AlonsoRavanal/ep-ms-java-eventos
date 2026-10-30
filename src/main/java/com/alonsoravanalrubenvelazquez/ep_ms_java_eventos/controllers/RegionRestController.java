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

import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.models.Region;
import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.services.RegionService;

@RestController
@RequestMapping("/api/regiones")
public class RegionRestController {

    private final RegionService regionService;

    public RegionRestController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<List<Region>> listar() {
        return ResponseEntity.ok(regionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> obtener(@PathVariable Long id) {
        return regionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Region> crear(@RequestBody Region region) {
        Region creada = regionService.save(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> actualizar(@PathVariable Long id, @RequestBody Region region) {
        if (!regionService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(regionService.update(id, region));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!regionService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        regionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
