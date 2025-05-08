package com.example.labo2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Departamento {
    // Getters y Setters
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private String nombre;

    @OneToMany(mappedBy = "departamento")
    private List<Empleado> empleados;

}