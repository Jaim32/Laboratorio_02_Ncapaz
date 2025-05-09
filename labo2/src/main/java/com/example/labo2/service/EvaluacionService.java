package com.example.labo2.service;

import com.example.labo2.entity.Empleado;
import com.example.labo2.entity.Evaluacion;
import com.example.labo2.repository.EmpleadoRepository;
import com.example.labo2.repository.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Scanner;

@Service
public class EvaluacionService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    private final Scanner scanner = new Scanner(System.in);

    public void evaluarEmpleado() {
        System.out.print("Ingrese el ID del empleado a evaluar: ");
        Long empleadoId = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElse(null);

        if (empleado == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        System.out.println("Empleado a evaluar:");
        System.out.println("• ID: " + empleado.getId());
        System.out.println("• Nombre: " + empleado.getNombre() + " " + empleado.getApellido());
        System.out.println("• Puesto: " + empleado.getPuesto());

        System.out.print("Ingrese el comentario de evaluación: ");
        String comentario = scanner.nextLine();

        System.out.print("Ingrese el puntaje de evaluación (1-10): ");
        Integer puntaje = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (puntaje < 1 || puntaje > 10) {
            System.out.println("El puntaje debe estar entre 1 y 10.");
            return;
        }

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setEmpleado(empleado);
        evaluacion.setComentario(comentario);
        evaluacion.setFecha(LocalDate.now());
        evaluacion.setPuntaje(puntaje);

        evaluacionRepository.save(evaluacion);
        System.out.println("Evaluación registrada correctamente.");
    }
}