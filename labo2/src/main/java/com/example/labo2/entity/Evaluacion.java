package com.example.labo2.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private int puntaje;
    private String comentarios;

    @ManyToOne
    private Empleado empleado;
}