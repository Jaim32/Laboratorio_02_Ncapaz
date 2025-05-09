package com.example.labo2.controller;

import com.example.labo2.entity.Capacitacion;
import com.example.labo2.repository.CapacitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capacitaciones")
public class CapacitacionController {

    @Autowired
    private CapacitacionRepository capacitacionRepository;

    @PostMapping
    public Capacitacion crearCapacitacion(@RequestBody Capacitacion capacitacion) {
        return capacitacionRepository.save(capacitacion);
    }

    @GetMapping
    public List<Capacitacion> listarCapacitaciones() {
        return capacitacionRepository.findAll();
    }
}