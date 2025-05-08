package com.example.labo2.entity;

import jakarta.persistence.*;

@Entity
public class Tecnologia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String version;
}