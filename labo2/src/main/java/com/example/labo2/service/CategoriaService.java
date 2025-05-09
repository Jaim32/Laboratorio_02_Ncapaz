package com.example.labo2.service;

import com.example.labo2.entity.Categoria;
import com.example.labo2.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public void eliminarPorId(Long id) {
        categoriaRepository.deleteById(id);
    }


    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    private final Scanner scanner = new Scanner(System.in);

    public void gestionarCategorias() {
        int opcion;

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         GESTIÓN DE CATEGORÍAS        ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Ver categorías                    ║");
            System.out.println("║ 2. Registrar nueva categoría         ║");
            System.out.println("║ 0. Volver al menú principal          ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> listarCategorias();
                case 2 -> registrarCategoria();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías registradas.");
        } else {
            System.out.println("Lista de categorías:");
            categorias.forEach(c -> System.out.println("• " + c.getId() + " - " + c.getNombre()));
        }
    }

    private void registrarCategoria() {
        Categoria categoria = new Categoria();

        System.out.print("Nombre de la categoría: ");
        categoria.setNombre(scanner.nextLine());

        categoriaRepository.save(categoria);
        System.out.println("Categoría registrada correctamente.");
    }
}