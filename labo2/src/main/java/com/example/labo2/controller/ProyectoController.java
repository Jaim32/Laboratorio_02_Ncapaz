package com.example.labo2.controller;

import com.example.labo2.entity.Proyecto;
import com.example.labo2.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public List<Proyecto> listarTodos() {
        return proyectoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> buscarPorId(@PathVariable Long id) {
        return proyectoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proyecto> guardar(
            @RequestBody Proyecto proyecto,
            @RequestParam Long liderId,
            @RequestBody List<Long> empleadosIds) {
        Proyecto nuevoProyecto = proyectoService.guardar(proyecto, liderId, empleadosIds);
        return ResponseEntity.ok(nuevoProyecto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        if (proyectoService.existePorId(id)) {
            proyectoService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{proyectoId}/asignar-empleados")
    public ResponseEntity<Proyecto> asignarEmpleados(
            @PathVariable Long proyectoId,
            @RequestParam Long liderId,
            @RequestBody List<Long> empleadosIds) {
        Proyecto proyecto = proyectoService.asignarEmpleados(proyectoId, liderId, empleadosIds);
        return ResponseEntity.ok(proyecto);
    }
}