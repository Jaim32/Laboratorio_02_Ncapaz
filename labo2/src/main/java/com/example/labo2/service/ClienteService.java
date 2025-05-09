package com.example.labo2.service;

import com.example.labo2.entity.Cliente;
import com.example.labo2.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    private final Scanner scanner = new Scanner(System.in);

    public void gestionarClientes() {
        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║           GESTIÓN DE CLIENTES        ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Ver clientes                      ║");
            System.out.println("║ 2. Registrar nuevo cliente           ║");
            System.out.println("║ 0. Volver al menú principal          ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> listarClientes();
                case 2 -> registrarCliente();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("Lista de clientes:");
            clientes.forEach(c -> System.out.println("• " + c.getNombre() + " " + c.getApellido() + " (Industria: " + c.getIndustria() + ", Contacto: " + c.getEmail() + ")"));
        }
    }

    private void registrarCliente() {
        Cliente cliente = new Cliente();

        System.out.print("Nombre: ");
        cliente.setNombre(scanner.nextLine());

        System.out.print("Apellido: ");
        cliente.setApellido(scanner.nextLine());

        System.out.print("Industria: ");
        cliente.setIndustria(scanner.nextLine());

        System.out.print("Contacto (email o teléfono): ");
        cliente.setEmail(scanner.nextLine());

        clienteRepository.save(cliente);
        System.out.println("Cliente registrado correctamente.");
    }
}