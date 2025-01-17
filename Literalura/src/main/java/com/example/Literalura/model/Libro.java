package com.example.Literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;
    private String idioma;
    private Long descargas;

    public Libro(DatosLibro datosLibro)
    {
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autores().get(0));
        this.idioma = datosLibro.idiomas().get(0);
        this.descargas = datosLibro.descargas();
    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    public Libro() {
    }

    @Override
    public String toString() {
        return String.format("\nLibro {\n" +
                        "  Título: '%s',\n" +
                        "  Autor: %s,\n" +
                        "  Idiomas: %s,\n" +
                        "  Descargas: %d\n" +
                        "}",
                titulo,
                autor,
                idioma,
                descargas);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Libro libro = (Libro) obj;
        return titulo.equals(libro.titulo);
    }

    @Override
    public int hashCode() {
        return titulo.hashCode();
    }
}
