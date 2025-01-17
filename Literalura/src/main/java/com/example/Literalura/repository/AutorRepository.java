package com.example.Literalura.repository;

import com.example.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);
    @Query("SELECT a FROM Autor a WHERE (a.fallecimiento = 0 OR a.fallecimiento >= :ano) ")
    List<Autor> findAutoresVivosEnAno(@Param("ano") int ano);
}
