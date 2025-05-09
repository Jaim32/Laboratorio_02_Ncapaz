package com.example.labo2.service;

import com.example.labo2.entity.Departamento;
import com.example.labo2.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Departamento> listarTodos() {
        return departamentoRepository.findAll();
    }

    public Departamento guardar(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }
}