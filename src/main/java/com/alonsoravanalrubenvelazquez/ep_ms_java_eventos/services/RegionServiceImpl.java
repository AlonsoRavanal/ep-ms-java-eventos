package com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.models.Region;
import com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.repositories.RegionRepository;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Region> findById(Long id) {
        return regionRepository.findById(id);
    }

    @Override
    @Transactional
    public Region save(Region region) {
        return regionRepository.save(region);
    }

    @Override
    @Transactional
    public Region update(Long id, Region region) {
        Region existente = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region no encontrada con id: " + id));

        existente.setNombre(region.getNombre());

        return regionRepository.save(existente);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        regionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return regionRepository.existsById(id);
    }
}
