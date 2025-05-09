package com.example.labo2.service;

import com.example.labo2.entity.Area;
import com.example.labo2.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @PostConstruct
    public void inicializarAreas() {
        if (areaRepository.count() == 0) {
            Area tecnologia = new Area();
            tecnologia.setNombre("Tecnología");
            tecnologia.setTarifa(500.0);

            Area metodologias = new Area();
            metodologias.setNombre("Metodologías de Trabajo");
            metodologias.setTarifa(300.0);

            Area financieros = new Area();
            financieros.setNombre("Financieros");
            financieros.setTarifa(400.0);

            Area entrenamiento = new Area();
            entrenamiento.setNombre("Entrenamiento de Empleados");
            entrenamiento.setTarifa(200.0);

            areaRepository.saveAll(List.of(tecnologia, metodologias, financieros, entrenamiento));
        }
    }

    public List<Area> listarTodas() {
        return areaRepository.findAll();
    }
}