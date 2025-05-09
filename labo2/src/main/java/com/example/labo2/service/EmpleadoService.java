package com.example.labo2.service;

import com.example.labo2.entity.Empleado;
import com.example.labo2.repository.EmpleadoRepository;
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
        empleados.forEach(e -> e.getDepartamentos().size()); // Inicializar la colección departamentos
        return empleados;
    }

    @Transactional(readOnly = true)
    public Optional<Empleado> buscarPorId(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        empleado.ifPresent(e -> e.getDepartamentos().size()); // Inicializar la colección departamentos
        return empleado;
    }

    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public void eliminarPorId(Long id) {
        empleadoRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return empleadoRepository.existsById(id);
    }
}