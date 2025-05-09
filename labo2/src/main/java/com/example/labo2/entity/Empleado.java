package com.example.labo2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Empleado extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id; // Clave primaria

    @ToString.Include
    @Column(nullable = false)
    private String nombre;

    @ToString.Include
    @Column(nullable = false)
    private String apellido;

    @ToString.Include
    @Column(nullable = false, unique = true)
    private String email; // Campo único para identificar al empleado

    private LocalDate fechaIngreso;

    @ToString.Include
    private String puesto;

    @ManyToMany
    @JoinTable(
            name = "empleado_departamento",
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "departamento_id")
    )
    private Set<Departamento> departamentos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "mentoria",
            joinColumns = @JoinColumn(name = "mentor_id"),
            inverseJoinColumns = @JoinColumn(name = "aprendiz_id"))
    private Set<Empleado> mentores = new HashSet<>();

    @ManyToMany(mappedBy = "mentores")
    private Set<Empleado> aprendices = new HashSet<>();

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Evaluacion> evaluaciones;

    @ManyToMany
    private Set<Capacitacion> capacitaciones;

    @ManyToMany(mappedBy = "empleados")
    private Set<Proyecto> proyectos;

    @OneToMany(mappedBy = "lider")
    private List<Proyecto> lideraProyectos;
}