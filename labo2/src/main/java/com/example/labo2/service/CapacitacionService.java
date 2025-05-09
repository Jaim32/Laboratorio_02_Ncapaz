package com.example.labo2.service;

import com.example.labo2.entity.Capacitacion;
import com.example.labo2.repository.CapacitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Service
public class CapacitacionService {

    @Autowired
    private CapacitacionRepository capacitacionRepository;

    private final Scanner scanner = new Scanner(System.in);

    public void gestionarCapacitaciones() {
        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║       GESTIÓN DE CAPACITACIONES      ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Ver capacitaciones                ║");
            System.out.println("║ 2. Registrar nueva capacitación      ║");
            System.out.println("║ 0. Volver al menú principal          ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> listarCapacitaciones();
                case 2 -> registrarCapacitacion();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void listarCapacitaciones() {
        List<Capacitacion> capacitaciones = capacitacionRepository.findAll();
        if (capacitaciones.isEmpty()) {
            System.out.println("No hay capacitaciones registradas.");
        } else {
            System.out.println("Lista de capacitaciones:");
            capacitaciones.forEach(c -> System.out.println("• " + c.getId() + " - " + c.getTema() + " - " + c.getFecha() + " - " + c.getDuracionHoras() + " horas"));
        }
    }

    private void registrarCapacitacion() {
        Capacitacion capacitacion = new Capacitacion();

        System.out.print("Tema de la capacitación: ");
        capacitacion.setTema(scanner.nextLine());

        System.out.print("Fecha de la capacitación (YYYY-MM-DD): ");
        capacitacion.setFecha(LocalDate.parse(scanner.nextLine()));

        System.out.print("Duración en horas: ");
        capacitacion.setDuracionHoras(scanner.nextInt());
        scanner.nextLine(); // Limpiar buffer

        capacitacionRepository.save(capacitacion);
        System.out.println("Capacitación registrada correctamente.");
    }
}
