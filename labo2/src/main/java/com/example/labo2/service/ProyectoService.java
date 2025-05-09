package com.example.labo2.service;

import com.example.labo2.entity.Empleado;
import com.example.labo2.entity.Proyecto;
import com.example.labo2.repository.EmpleadoRepository;
import com.example.labo2.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> buscarPorId(Long id) {
        return proyectoRepository.findById(id);
    }

    public Proyecto guardar(Proyecto proyecto, Long liderId, List<Long> empleadosIds) {
        if (empleadosIds == null || empleadosIds.size() < 2) {
            throw new IllegalArgumentException("El proyecto debe tener al menos dos empleados asignados, incluyendo un líder.");
        }

        Empleado lider = empleadoRepository.findById(liderId)
                .orElseThrow(() -> new IllegalArgumentException("Líder no encontrado"));

        if (!empleadosIds.contains(liderId)) {
            throw new IllegalArgumentException("El líder debe ser un participante del proyecto.");
        }

        Set<Empleado> empleados = new HashSet<>(empleadoRepository.findAllById(empleadosIds));
        if (empleados.size() < 2) {
            throw new IllegalArgumentException("El proyecto debe tener al menos dos empleados válidos asignados.");
        }

        proyecto.setLider(lider);
        proyecto.setEmpleados(empleados);

        return proyectoRepository.save(proyecto);
    }

    public void eliminarPorId(Long id) {
        proyectoRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return proyectoRepository.existsById(id);
    }

    public Proyecto asignarEmpleados(Long proyectoId, Long liderId, List<Long> empleadosIds) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        Empleado lider = empleadoRepository.findById(liderId)
                .orElseThrow(() -> new IllegalArgumentException("Líder no encontrado"));

        if (!empleadosIds.contains(liderId)) {
            throw new IllegalArgumentException("El líder debe ser un participante del proyecto");
        }

        Set<Empleado> empleados = new HashSet<>(empleadoRepository.findAllById(empleadosIds));
        if (empleados.isEmpty()) {
            throw new IllegalArgumentException("El proyecto debe tener al menos un participante");
        }

        proyecto.setLider(lider);
        proyecto.setEmpleados(empleados);

        return proyectoRepository.save(proyecto);
    }
}