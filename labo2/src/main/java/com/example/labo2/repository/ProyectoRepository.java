package com.example.labo2.repository;

import com.example.labo2.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    @Query("SELECT p FROM Proyecto p " +
            "LEFT JOIN FETCH p.categoria " +
            "LEFT JOIN FETCH p.cliente " +
            "LEFT JOIN FETCH p.lider " +
            "LEFT JOIN FETCH p.empleados")
    List<Proyecto> findAllWithDetails();
}