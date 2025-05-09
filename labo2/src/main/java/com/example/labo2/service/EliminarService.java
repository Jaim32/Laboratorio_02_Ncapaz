package com.example.labo2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class EliminarService {

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private TecnologiaService tecnologiaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private ProyectoService proyectoService;

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenuEliminar() {
        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║          MENÚ DE ELIMINACIONES        ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Eliminar Tecnología               ║");
            System.out.println("║ 2. Eliminar Cliente                  ║");
            System.out.println("║ 3. Eliminar Empleado                 ║");
            System.out.println("║ 4. Eliminar Proyecto                 ║");
            System.out.println("║ 0. Salir                             ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> eliminarTecnologia();
                case 2 -> eliminarCliente();
                case 3 -> eliminarEmpleado();
                case 4 -> eliminarProyecto();
                case 0 -> System.out.println("Saliendo del menú de eliminaciones...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void eliminarDepartamento() {
        System.out.print("Ingrese el ID del departamento a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        if (confirmarEliminacion()) {
            try {
                departamentoService.eliminarPorId(id);
                System.out.println("Departamento eliminado correctamente.");
            } catch (Exception e) {
                System.out.println("Error al eliminar el departamento: " + e.getMessage());
            }
        }
    }

    private void eliminarTecnologia() {
        System.out.print("Ingrese el ID de la tecnología a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        if (confirmarEliminacion()) {
            try {
                tecnologiaService.eliminarPorId(id);
                System.out.println("Tecnología eliminada correctamente.");
            } catch (Exception e) {
                System.out.println("Error al eliminar la tecnología: " + e.getMessage());
            }
        }
    }

    private void eliminarCliente() {
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        if (confirmarEliminacion()) {
            try {
                clienteService.eliminarPorId(id);
                System.out.println("Cliente eliminado correctamente.");
            } catch (Exception e) {
                System.out.println("Error al eliminar el cliente: " + e.getMessage());
            }
        }
    }

    private void eliminarCategoria() {
        System.out.print("Ingrese el ID de la categoría a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        if (confirmarEliminacion()) {
            try {
                categoriaService.eliminarPorId(id);
                System.out.println("Categoría eliminada correctamente.");
            } catch (Exception e) {
                System.out.println("Error al eliminar la categoría: " + e.getMessage());
            }
        }
    }

    private void eliminarEmpleado() {
        System.out.print("Ingrese el ID del empleado a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        if (confirmarEliminacion()) {
            try {
                empleadoService.eliminarPorId(id);
                System.out.println("Empleado eliminado correctamente.");
            } catch (Exception e) {
                System.out.println("Error al eliminar el empleado: " + e.getMessage());
            }
        }
    }

    private void eliminarProyecto() {
        System.out.print("Ingrese el ID del proyecto a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        if (confirmarEliminacion()) {
            try {
                proyectoService.eliminarPorId(id);
                System.out.println("Proyecto eliminado correctamente.");
            } catch (Exception e) {
                System.out.println("Error al eliminar el proyecto: " + e.getMessage());
            }
        }
    }

    private boolean confirmarEliminacion() {
        System.out.print("¿Está seguro de que desea eliminar este elemento? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        return confirmacion.equals("s");
    }
}