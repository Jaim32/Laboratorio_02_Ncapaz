package com.example.labo2.service;

import com.example.labo2.entity.Empleado;
import com.example.labo2.entity.Proyecto;
import com.example.labo2.entity.Tecnologia;
import com.example.labo2.repository.EmpleadoRepository;
import com.example.labo2.repository.ProyectoRepository;
import com.example.labo2.repository.TecnologiaRepository;
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

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    /**
     * Lista todos los proyectos con sus detalles.
     */
    @Transactional(readOnly = true)
    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAllWithDetails();
    }

    /**
     * Busca un proyecto por su ID con detalles.
     */
    @Transactional(readOnly = true)
    public Optional<Proyecto> buscarPorId(Long id) {
        return proyectoRepository.findByIdWithDetails(id);
    }

    /**
     * Guarda un proyecto con líder y empleados asignados.
     */
    @Transactional
    public Proyecto guardar(Proyecto proyecto, Long liderId, List<Long> empleadosIds) {
        validarEmpleadosYAsignar(proyecto, liderId, empleadosIds);
        return proyectoRepository.save(proyecto);
    }

    /**
     * Guarda un proyecto sin asignar empleados ni líder.
     */
    @Transactional
    public Proyecto guardar(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    /**
     * Elimina un proyecto por su ID.
     */
    @Transactional
    public void eliminarPorId(Long id) {
        proyectoRepository.deleteById(id);
    }

    /**
     * Verifica si un proyecto existe por su ID.
     */
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return proyectoRepository.existsById(id);
    }

    /**
     * Asigna empleados y un líder a un proyecto existente.
     */
    @Transactional
    public Proyecto asignarEmpleados(Long proyectoId, Long liderId, List<Long> empleadosIds) {
        Proyecto proyecto = proyectoRepository.findByIdWithDetails(proyectoId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        validarEmpleadosYAsignar(proyecto, liderId, empleadosIds);
        return proyectoRepository.save(proyecto);
    }

    /**
     * Asigna tecnologías a un proyecto existente.
     */
    @Transactional
    public Proyecto asignarTecnologias(Long proyectoId, List<Long> tecnologiasIds) {
        Proyecto proyecto = proyectoRepository.findByIdWithDetails(proyectoId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        if (proyecto.getTecnologias() == null) {
            proyecto.setTecnologias(new HashSet<>());
        }

        // Validar y asignar tecnologías
        Set<Tecnologia> tecnologias = new HashSet<>(tecnologiaRepository.findAllById(tecnologiasIds));
        if (tecnologias.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron tecnologías válidas para asignar.");
        }

        proyecto.getTecnologias().addAll(tecnologias);
        return proyectoRepository.save(proyecto);
    }

    /**
     * Valida y asigna empleados y líder a un proyecto.
     */
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