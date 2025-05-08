package com.example.labo2.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Capacitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tema;
    private LocalDate fecha;
    private int duracionHoras;

    @ManyToMany(mappedBy = "capacitaciones")
    private Set<Empleado> empleados;
}