package com.example.labo2.service;

import com.example.labo2.entity.Empleado;
import com.example.labo2.entity.Proyecto;
import com.example.labo2.entity.Tecnologia;
import com.example.labo2.repository.TecnologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TecnologiaService {

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    @Autowired
    private ProyectoService proyectoService;

    public void eliminarPorId(Long id) {
        tecnologiaRepository.deleteById(id);
    }

    private final Scanner scanner = new Scanner(System.in);

    public void gestionarTecnologias() {
        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         GESTIÓN DE TECNOLOGÍAS       ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Ver tecnologías                   ║");
            System.out.println("║ 2. Registrar nueva tecnología        ║");
            System.out.println("║ 3. Asignar tecnología a proyecto     ║");
            System.out.println("║ 0. Volver al menú principal          ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            try {
                switch (opcion) {
                    case 1 -> listarTecnologias();
                    case 2 -> registrarTecnologia();
                    case 3 -> asignarTecnologiaAProyecto();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
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

    private void asignarTecnologiaAProyecto() {
        try {
            System.out.print("ID del proyecto al que deseas asignar tecnologías: ");
            Long proyectoId = scanner.nextLong();
            scanner.nextLine(); // Limpiar buffer

            // Depuración: listar todos los proyectos disponibles
            System.out.println("Proyectos disponibles en la base de datos:");
            List<Proyecto> proyectos = proyectoService.listarTodos();
            if (proyectos.isEmpty()) {
                System.out.println("No hay proyectos registrados en la base de datos.");
                return;
            }
            proyectos.forEach(p -> System.out.println("• ID: " + p.getId() + ", Nombre: " + p.getNombre()));

            // Buscar el proyecto por ID
            Proyecto proyecto = proyectoService.buscarPorId(proyectoId)
                    .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado."));

            System.out.println("Proyecto seleccionado:");
            System.out.println("• ID: " + proyecto.getId());
            System.out.println("  Código: " + proyecto.getCodigo());
            System.out.println("  Nombre: " + proyecto.getNombre());

            // Listar tecnologías disponibles
            System.out.println("\nLista de tecnologías disponibles:");
            List<Tecnologia> tecnologias = listarTecnologiasDesdeBD();
            if (tecnologias.isEmpty()) {
                System.out.println("No hay tecnologías disponibles para asignar.");
                return;
            }
            tecnologias.forEach(t -> System.out.println(t.getId() + ". " + t.getNombre()));

            // Solicitar IDs de tecnologías a asignar
            System.out.print("\nIngresa los IDs de las tecnologías a asignar (separados por comas): ");
            String tecnologiasInput = scanner.nextLine();
            List<Long> tecnologiasIds = Arrays.stream(tecnologiasInput.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            // Filtrar tecnologías válidas
            Set<Tecnologia> tecnologiasAsignadas = tecnologias.stream()
                    .filter(t -> tecnologiasIds.contains(t.getId()))
                    .collect(Collectors.toSet());

            if (tecnologiasAsignadas.isEmpty()) {
                System.out.println("No se encontraron tecnologías válidas para asignar.");
                return;
            }

            // Asignar tecnologías al proyecto
            proyecto.getTecnologias().addAll(tecnologiasAsignadas);

            // Validar y guardar el proyecto
            Long liderId = Optional.ofNullable(proyecto.getLider())
                    .map(Empleado::getId)
                    .orElseThrow(() -> new IllegalArgumentException("El proyecto no tiene un líder asignado."));
            List<Long> empleadosIds = proyecto.getEmpleados().stream()
                    .map(Empleado::getId)
                    .collect(Collectors.toList());

            if (empleadosIds.isEmpty()) {
                throw new IllegalArgumentException("El proyecto no tiene empleados asignados.");
            }

            proyectoService.guardar(proyecto, liderId, empleadosIds);

            System.out.println("Tecnologías asignadas correctamente al proyecto.");
        } catch (Exception e) {
            System.out.println("Error al asignar tecnologías: " + e.getMessage());
        }
    }
    public List<Tecnologia> listarTecnologiasDesdeBD() {
        return tecnologiaRepository.findAll();
    }
}