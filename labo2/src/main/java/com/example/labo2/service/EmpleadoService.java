package com.example.labo2.service;

import com.example.labo2.entity.Empleado;
import com.example.labo2.repository.EmpleadoRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public List<Empleado> listarTodos() {
        List<Empleado> empleados = empleadoRepository.findAll();
        empleados.forEach(e -> {
            Hibernate.initialize(e.getDepartamentos()); // Inicializar departamentos
            Hibernate.initialize(e.getMentores()); // Inicializar mentores
        });
        return empleados;
    }

    @Transactional(readOnly = true)
    public Optional<Empleado> buscarPorId(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        empleado.ifPresent(e -> {
            Hibernate.initialize(e.getDepartamentos()); // Inicializar departamentos
            Hibernate.initialize(e.getMentores()); // Inicializar mentores
        });
        return empleado;
    }

    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public void eliminarPorId(Long id) {
        try {
            empleadoRepository.deleteById(id);
            System.out.println("Empleado eliminado correctamente.");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            System.err.println("Error al eliminar el empleado: No se puede eliminar porque está siendo referenciado por otros registros.");
        }
    }

    public boolean existePorId(Long id) {
        return empleadoRepository.existsById(id);
    }

    @Transactional
    public void asignarMentor(Long empleadoId, Long mentorId) {
        if (empleadoId.equals(mentorId)) {
            throw new IllegalArgumentException("Un empleado no puede ser su propio mentor.");
        }

        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
        Empleado mentor = empleadoRepository.findById(mentorId)
                .orElseThrow(() -> new IllegalArgumentException("Mentor no encontrado"));

        if (empleado.getMentores().contains(mentor)) {
            throw new IllegalArgumentException("El mentor ya está asignado a este empleado.");
        }

        empleado.getMentores().add(mentor);
        mentor.getAprendices().add(empleado);

        empleadoRepository.save(empleado);
        empleadoRepository.save(mentor);
    }
}