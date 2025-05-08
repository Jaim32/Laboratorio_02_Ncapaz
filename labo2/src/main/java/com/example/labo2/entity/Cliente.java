package com.example.labo2.entity;

import jakarta.persistence.*;

@Entity
public class Cliente extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave primaria

    private String industria;

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getIndustria() {
        return industria;
    }
    public void setIndustria(String industria) {
        this.industria = industria;
    }
}