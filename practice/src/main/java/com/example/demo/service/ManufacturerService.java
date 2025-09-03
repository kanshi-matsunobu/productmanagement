package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Manufacturer;
import com.example.demo.repository.ManufacturerRepository;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Manufacturer findById(int id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    public Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public Manufacturer update(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }
    
    public void delete(int id) {
        manufacturerRepository.deleteById(id);
    }
}