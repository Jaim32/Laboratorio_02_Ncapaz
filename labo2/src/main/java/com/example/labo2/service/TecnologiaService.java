package com.example.labo2.service;

import com.example.labo2.entity.Tecnologia;
import com.example.labo2.repository.TecnologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class TecnologiaService {

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    private final Scanner scanner = new Scanner(System.in);

    public void gestionarTecnologias() {
        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         GESTIÓN DE TECNOLOGÍAS       ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Ver tecnologías                   ║");
            System.out.println("║ 2. Registrar nueva tecnología        ║");
            System.out.println("║ 0. Volver al menú principal          ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> listarTecnologias();
                case 2 -> registrarTecnologia();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void listarTecnologias() {
        List<Tecnologia> tecnologias = listarTecnologiasDesdeBD();
        if (tecnologias.isEmpty()) {
            System.out.println("No hay tecnologías registradas.");
        } else {
            System.out.println("Lista de tecnologías:");
            tecnologias.forEach(t -> System.out.println("• " + t.getNombre() + " (Versión: " + t.getVersion() + ")"));
        }
    }

    private void registrarTecnologia() {
        System.out.print("Nombre de la tecnología: ");
        String nombre = scanner.nextLine();

        System.out.print("Versión de la tecnología: ");
        String version = scanner.nextLine();

        registrarTecnologiaEnBD(nombre, version);
        System.out.println("Tecnología registrada correctamente.");
    }

    public void registrarTecnologiaEnBD(String nombre, String version) {
        Tecnologia tecnologia = new Tecnologia();
        tecnologia.setNombre(nombre);
        tecnologia.setVersion(version);
        tecnologiaRepository.save(tecnologia);
    }

    public List<Tecnologia> listarTecnologiasDesdeBD() {
        return tecnologiaRepository.findAll();
    }
}