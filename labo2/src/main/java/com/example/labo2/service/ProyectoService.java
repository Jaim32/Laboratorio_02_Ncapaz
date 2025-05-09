package com.example.labo2.service;

import com.example.labo2.entity.Empleado;
import com.example.labo2.entity.Proyecto;
import com.example.labo2.repository.EmpleadoRepository;
import com.example.labo2.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAllWithDetails();
    }

    @Transactional(readOnly = true)
    public Optional<Proyecto> buscarPorId(Long id) {
        return proyectoRepository.findById(id);
    }

    @Transactional
    public Proyecto guardar(Proyecto proyecto, Long liderId, List<Long> empleadosIds) {
        validarEmpleadosYAsignar(proyecto, liderId, empleadosIds);
        return proyectoRepository.save(proyecto);
    }

    @Transactional
    public void eliminarPorId(Long id) {
        proyectoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return proyectoRepository.existsById(id);
    }

    @Transactional
    public Proyecto asignarEmpleados(Long proyectoId, Long liderId, List<Long> empleadosIds) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
        validarEmpleadosYAsignar(proyecto, liderId, empleadosIds);
        return proyectoRepository.save(proyecto);
    }

    private void validarEmpleadosYAsignar(Proyecto proyecto, Long liderId, List<Long> empleadosIds) {
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
    }
}