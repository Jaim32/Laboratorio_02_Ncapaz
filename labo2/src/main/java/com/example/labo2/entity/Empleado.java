package com.example.labo2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Empleado extends Persona {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave primaria

    @Setter
    @Getter
    private LocalDate fechaIngreso;

    @Setter
    @Getter
    private String puesto;

    @Setter
    @Getter
    @ManyToMany
    @JoinTable(
            name = "empleado_departamento",
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "departamento_id")
    )
    private Set<Departamento> departamentos = new HashSet<>();

    @Setter
    @Getter
    @ManyToMany
    @JoinTable(name = "mentoria",
            joinColumns = @JoinColumn(name = "mentor_id"),
            inverseJoinColumns = @JoinColumn(name = "aprendiz_id"))
    private Set<Empleado> mentores = new HashSet<>();

    @Setter
    @Getter
    @ManyToMany(mappedBy = "mentores")
    private Set<Empleado> aprendices = new HashSet<>();

    @Setter
    @Getter
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Evaluacion> evaluaciones;

    @Setter
    @Getter
    @ManyToMany
    private Set<Capacitacion> capacitaciones;

    @Setter
    @Getter
    @ManyToMany(mappedBy = "empleados")
    private Set<Proyecto> proyectos;

    @OneToMany(mappedBy = "lider")
    private List<Proyecto> lideraProyectos;
}