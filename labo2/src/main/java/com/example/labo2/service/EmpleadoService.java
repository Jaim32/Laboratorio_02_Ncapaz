package com.example.labo2.service;

import com.example.labo2.entity.Empleado;
import com.example.labo2.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> buscarPorId(Long id) {
        return empleadoRepository.findById(id);
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