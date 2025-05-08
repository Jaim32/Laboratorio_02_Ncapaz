package com.example.labo2.service;

import com.example.labo2.entity.Proyecto;
import com.example.labo2.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> buscarPorId(Long id) {
        return proyectoRepository.findById(id);
    }

    public Proyecto guardar(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void eliminarPorId(Long id) {
        proyectoRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return proyectoRepository.existsById(id);
    }
}