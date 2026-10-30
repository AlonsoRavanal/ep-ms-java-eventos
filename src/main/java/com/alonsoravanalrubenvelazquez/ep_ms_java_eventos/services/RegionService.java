package com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.services;

import java.util.List;
import java.util.Optional;

import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.models.Region;

public interface RegionService {

    List<Region> findAll();

    Optional<Region> findById(Long id);

    Region save(Region region);

    Region update(Long id, Region region);

    void deleteById(Long id);

    boolean existsById(Long id);
}
