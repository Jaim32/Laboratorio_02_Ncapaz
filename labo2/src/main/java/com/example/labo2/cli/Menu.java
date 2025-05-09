package com.example.labo2.cli;

import com.example.labo2.entity.*;
import com.example.labo2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class Menu {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private TecnologiaService tecnologiaService; // Inyección del servicio

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private CapacitacionService capacitacionService;

    private final Scanner scanner = new Scanner(System.in);

    public void mostrar() {
        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║        MENÚ PRINCIPAL - LABO2        ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Listar empleados                  ║");
            System.out.println("║ 2. Crear nuevo empleado              ║");
            System.out.println("║ 3. Listar proyectos                  ║");
            System.out.println("║ 4. Crear nuevo proyecto              ║");
            System.out.println("║ 5. Departamentos                     ║");
            System.out.println("║ 6. Tecnologías                       ║");
            System.out.println("║ 7. Clientes                          ║");
            System.out.println("║ 8. Categorías                        ║");
            System.out.println("║ 9. Evaluar empleado                  ║");
            System.out.println("║ 10. Capacitaciones                   ║");
            System.out.println("║ 0. Salir                             ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> listarEmpleados();
                case 2 -> crearEmpleado();
                case 3 -> listarProyectos();
                case 4 -> crearProyecto();
                case 5 -> gestionarDepartamentos();
                case 6 -> tecnologiaService.gestionarTecnologias(); // Llamada al servicio
                case 7 -> clienteService.gestionarClientes();
                case 8 -> categoriaService.gestionarCategorias();
                case 9 -> evaluacionService.evaluarEmpleado();
                case 10 -> capacitacionService.gestionarCapacitaciones();
                case 0 -> System.out.println("¡Hasta pronto!");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private void listarEmpleados() {
        List<Empleado> empleados = empleadoService.listarTodos();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            System.out.println("Lista de empleados:");
            empleados.forEach(e -> {
                System.out.print("• " + e.getId() + " - " + e.getNombre() + " " + e.getApellido());
                if (!e.getDepartamentos().isEmpty()) {
                    System.out.print(" (Departamentos: ");
                    e.getDepartamentos().forEach(d -> System.out.print(d.getNombre() + " "));
                    System.out.print(")");
                }
                System.out.println();
            });
        }
    }

    private void crearEmpleado() {
        Empleado emp = new Empleado();

        System.out.print("Nombre: ");
        emp.setNombre(scanner.nextLine());

        System.out.print("Apellido: ");
        emp.setApellido(scanner.nextLine());

        System.out.print("Email: ");
        emp.setEmail(scanner.nextLine());

        System.out.print("Puesto: ");
        emp.setPuesto(scanner.nextLine());

        emp.setFechaIngreso(LocalDate.now());

        System.out.print("ID del departamento al que pertenece: ");
        Long departamentoId = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        Departamento departamento = departamentoService.listarTodos()
                .stream()
                .filter(d -> d.getId().equals(departamentoId))
                .findFirst()
                .orElse(null);

        if (departamento == null) {
            System.out.println("Departamento no encontrado.");
            return;
        }

        // Crear un conjunto y agregar el departamento
        Set<Departamento> departamentos = new HashSet<>();
        departamentos.add(departamento);
        emp.setDepartamentos(departamentos);

        empleadoService.guardar(emp);
        System.out.println("Empleado creado correctamente.");
    }

    private void listarProyectos() {
        List<Proyecto> proyectos = proyectoService.listarTodos();
        if (proyectos.isEmpty()) {
            System.out.println("No hay proyectos registrados.");
        } else {
            System.out.println("Lista de proyectos:");
            proyectos.forEach(p -> {
                System.out.println("• ID: " + p.getId());
                System.out.println("  Código: " + p.getCodigo());
                System.out.println("  Nombre: " + p.getNombre());
                System.out.println("  Cliente: " + (p.getCliente() != null ? p.getCliente().getNombre() : "No asignado"));
                System.out.println("  Categoría: " + (p.getCategoria() != null ? p.getCategoria().getNombre() : "No asignada"));
                System.out.println("  Líder: " + (p.getLider() != null ? p.getLider().getNombre() + " " + p.getLider().getApellido() : "No asignado"));
                System.out.println("  Empleados participantes:");
                if (p.getEmpleados() != null && !p.getEmpleados().isEmpty()) {
                    p.getEmpleados().forEach(e -> System.out.println("    - " + e.getNombre() + " " + e.getApellido()));
                } else {
                    System.out.println("    No hay empleados asignados.");
                }
                System.out.println();
            });
        }
    }

    private void crearProyecto() {
        Proyecto proyecto = new Proyecto();

        System.out.print("Código del proyecto: ");
        proyecto.setCodigo(scanner.nextLine());

        System.out.print("Nombre del proyecto: ");
        proyecto.setNombre(scanner.nextLine());

        System.out.print("ID del cliente: ");
        Long clienteId = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        Cliente cliente = clienteService.listarTodos()
                .stream()
                .filter(c -> c.getId().equals(clienteId))
                .findFirst()
                .orElse(null);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        proyecto.setCliente(cliente);

        System.out.print("ID de la categoría: ");
        Long categoriaId = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        Categoria categoria = categoriaService.listarTodos()
                .stream()
                .filter(cat -> cat.getId().equals(categoriaId))
                .findFirst()
                .orElse(null);

        if (categoria == null) {
            System.out.println("Categoría no encontrada.");
            return;
        }
        proyecto.setCategoria(categoria);

        System.out.print("ID del líder del proyecto: ");
        Long liderId = scanner.nextLong();
        scanner.nextLine(); // Limpiar buffer

        System.out.print("IDs de los empleados participantes (separados por comas): ");
        String empleadosInput = scanner.nextLine();
        List<Long> empleadosIds = List.of(empleadosInput.split(","))
                .stream()
                .map(String::trim)
                .map(Long::parseLong)
                .toList();

        if (!empleadosIds.contains(liderId)) {
            System.out.println("El líder debe estar incluido en la lista de empleados.");
            return;
        }

        try {
            proyectoService.guardar(proyecto, liderId, empleadosIds);
            System.out.println("Proyecto creado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear el proyecto: " + e.getMessage());
        }
    }

    private void gestionarDepartamentos() {
        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║        GESTIÓN DE DEPARTAMENTOS      ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Ver departamentos                 ║");
            System.out.println("║ 2. Crear nuevo departamento          ║");
            System.out.println("║ 0. Volver al menú principal          ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> listarDepartamentos();
                case 2 -> crearDepartamento();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void listarDepartamentos() {
        List<Departamento> departamentos = departamentoService.listarTodos();
        if (departamentos.isEmpty()) {
            System.out.println("No hay departamentos registrados.");
        } else {
            System.out.println("Lista de departamentos:");
            departamentos.forEach(d -> System.out.println("• " + d.getId() + " - " + d.getNombre()));
        }
    }

    private void crearDepartamento() {
        Departamento departamento = new Departamento();

        System.out.print("Nombre del departamento: ");
        departamento.setNombre(scanner.nextLine());

        departamentoService.guardar(departamento);
        System.out.println("Departamento creado correctamente.");
    }
}