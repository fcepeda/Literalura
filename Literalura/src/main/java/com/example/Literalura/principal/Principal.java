package com.example.Literalura.principal;

import com.example.Literalura.model.Autor;
import com.example.Literalura.model.Libro;
import com.example.Literalura.model.RespuestaAPI;
import com.example.Literalura.repository.AutorRepository;
import com.example.Literalura.repository.LibroRepository;
import com.example.Literalura.service.ConsumoAPI;
import com.example.Literalura.service.Conversor;
import org.springframework.dao.DataIntegrityViolationException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private Conversor conversor = new Conversor();
    private List<Libro> libros = new ArrayList<>();;
    private List<Autor> autores = new ArrayList<>();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private Optional<Libro> libroBuscado;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu()
    {
        System.out.println("""
                **********************************
                   HOLA BIENVENIDO A LITERALURA
                **********************************
                """);
        var opcion = -1;
        while(opcion!=0)
        {
            var menu = """
                    
                    Por favor indica una de las siguientes opciones:
                    
                    1 - Buscar libro por titulo exacto
                    2 - Buscar libro por titulo parcial
                    3 - Libros buscados
                    4 - Autores de libros buscados
                    5 - Autores vivos segun año
                    6 - Cantidad de libros por idioma
                    
                    0 - Salir de la aplicacion
                    
                   """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion)
            {
                case 1:
                    obtenerLibroPorTitulo(true);
                    break;
                case 2:
                    obtenerLibroPorTitulo(false);
                    break;
                case 3:
                    mostrarLibrosBuscados();
                    break;
                case 4:
                    mostrarAutores();
                    break;
                case 5:
                    mostrarAutoresPorAno();
                    break;
                case 6:
                    mostrarCantidadLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    opcion = -1;
                    break;
            }

        }

    }

    private void mostrarCantidadLibrosPorIdioma() {
        List<Libro> libros = libroRepository.findAll();

        long totalLibros = libros.size();

        Map<String, Long> cantidadPorIdioma = libros.stream()
                .collect(Collectors.groupingBy(Libro::getIdioma, Collectors.counting()));

        Map<String, String> mapeoIdiomas = new HashMap<>();
        mapeoIdiomas.put("en", "Inglés");
        mapeoIdiomas.put("es", "Español");
        mapeoIdiomas.put("fr", "Francés");
        mapeoIdiomas.put("pt", "Portugués");

        System.out.println("\n Total de libros guardados: " + totalLibros);
        System.out.println("Cantidad de libros por idioma:");

        cantidadPorIdioma.forEach((idioma, cantidad) -> {
            String nombreIdioma = mapeoIdiomas.getOrDefault(idioma, "Otros idiomas");
            System.out.println("Idioma '" + nombreIdioma + "': " + cantidad + " libro(s)");
        });
    }

    private void mostrarAutoresPorAno() {

        System.out.println("Indique el año para ver que autores estaban/estan vivos en formato (YYYY) ej. 1920 :");
        try {
            var ano = teclado.nextInt();
            autores = autorRepository.findAutoresVivosEnAno(ano);
            autores.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("El formato no es correcto.");
        }

    }

    private void mostrarAutores() {
        autores = autorRepository.findAll();
        System.out.println("Autores: \n");
        autores.forEach(System.out::println);
    }

    private void mostrarLibrosBuscados() {
        libros = libroRepository.findAll();

        libros.forEach(System.out::println);
    }
    private void obtenerLibroPorTitulo(boolean exacto) {
        System.out.println("Indica el título del libro que deseas buscar:");
        String titulo = teclado.nextLine();

        //Comprobar si existe para no acceder a API
        libroBuscado = libroRepository.findByTituloIgnoreCase(titulo);

        if(libroBuscado.isPresent())
        {
            System.out.println("Libro encontrado: " + libroBuscado.get());
        }
        else
        {
            try {
                String url = "https://gutendex.com/books?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
                String json = consumoAPI.obtenerDatos(url);

                RespuestaAPI rapi = conversor.obtenerDatos(json, RespuestaAPI.class);
                Optional<Libro> libro;

                if(exacto)
                {
                    libro = rapi.results().stream()
                            .map(Libro::new)
                            .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                            .findFirst();
                }
                else
                {
                    libro = rapi.results().stream()
                            .map(Libro::new)
                            .findFirst();
                }
                if (libro.isPresent()) {
                    System.out.println("Libro encontrado: " + libro.get());
                    Libro auxLibro = libro.get();

                    Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(auxLibro.getTitulo());

                    if(libroExistente.isEmpty())
                    {
                        try {
                            Autor autorExistente = autorRepository.findByNombre(auxLibro.getAutor().getNombre());

                            if (autorExistente == null) {
                                autorRepository.save(auxLibro.getAutor());
                            } else {
                                auxLibro.setAutor(autorExistente);
                            }

                            libroRepository.save(auxLibro);

                        } catch (Exception e) {
                            System.out.println("Error al guardar el autor o el libro)");
                        }
                    }
                } else {
                    System.out.println("No se encontró ningún libro con ese título.");
                }


            } catch (Exception e) {
                System.err.println("Error al buscar el libro");
            }
        }
    }
}
