package com.example.labo2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class EditarService {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private EmpleadoService empleadoService;

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenuEditar() {
        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║          MENÚ DE EDICIONES           ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Editar Proyecto                   ║");
            System.out.println("║ 2. Editar Empleado                   ║");
            System.out.println("║ 0. Salir                             ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> editarProyecto();
                case 2 -> editarEmpleado();
                case 0 -> System.out.println("Saliendo del menú de ediciones...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void editarProyecto() {
        System.out.print("Ingrese el ID del proyecto a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        proyectoService.buscarPorId(id).ifPresentOrElse(proyecto -> {
            System.out.print("Nuevo nombre del proyecto (actual: " + proyecto.getNombre() + "): ");
            String nuevoNombre = scanner.nextLine();
            proyecto.setNombre(nuevoNombre);

            System.out.print("Nueva descripción del proyecto (actual: " + proyecto.getDescripcion() + "): ");
            String nuevaDescripcion = scanner.nextLine();
            proyecto.setDescripcion(nuevaDescripcion);

            proyectoService.guardar(proyecto);
            System.out.println("Proyecto editado correctamente.");
        }, () -> System.out.println("Proyecto no encontrado."));
    }

    private void editarEmpleado() {
        System.out.print("Ingrese el ID del empleado a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        empleadoService.buscarPorId(id).ifPresentOrElse(empleado -> {
            System.out.print("Nuevo nombre del empleado (actual: " + empleado.getNombre() + "): ");
            String nuevoNombre = scanner.nextLine();
            empleado.setNombre(nuevoNombre);

            System.out.print("Nuevo apellido del empleado (actual: " + empleado.getApellido() + "): ");
            String nuevoApellido = scanner.nextLine();
            empleado.setApellido(nuevoApellido);

            System.out.print("Nuevo email del empleado (actual: " + empleado.getEmail() + "): ");
            String nuevoEmail = scanner.nextLine();
            empleado.setEmail(nuevoEmail);

            System.out.print("Nuevo puesto del empleado (actual: " + empleado.getPuesto() + "): ");
            String nuevoPuesto = scanner.nextLine();
            empleado.setPuesto(nuevoPuesto);

            empleadoService.guardar(empleado);
            System.out.println("Empleado editado correctamente.");
        }, () -> System.out.println("Empleado no encontrado."));
    }
}