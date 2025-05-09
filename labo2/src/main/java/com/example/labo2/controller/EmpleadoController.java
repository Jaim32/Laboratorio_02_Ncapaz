package com.example.labo2.controller;

import com.example.labo2.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping("/{empleadoId}/asignar-mentor/{mentorId}")
    public ResponseEntity<Void> asignarMentor(@PathVariable Long empleadoId, @PathVariable Long mentorId) {
        empleadoService.asignarMentor(empleadoId, mentorId);
        return ResponseEntity.ok().build();
    }
}