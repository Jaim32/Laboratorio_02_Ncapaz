package com.example.labo2.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String nombre;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Area area;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Categoria categoria;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cliente cliente;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Empleado lider;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Empleado> empleados = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Tecnologia> tecnologias = new HashSet<>();

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Area getArea() {
        return area;
    }
    public void setArea(Area area) {
        this.area = area;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Empleado getLider() {
        return lider;
    }
    public void setLider(Empleado lider) {
        this.lider = lider;
    }
    public Set<Empleado> getEmpleados() {
        return empleados;
    }
    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }
    public Set<Tecnologia> getTecnologias() {
        return tecnologias;
    }
    public void setTecnologias(Set<Tecnologia> tecnologias) {
        this.tecnologias = tecnologias;
    }
}