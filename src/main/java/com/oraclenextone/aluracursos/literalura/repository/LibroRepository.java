package com.oraclenextone.aluracursos.literalura.repository;

import com.oraclenextone.aluracursos.literalura.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);
    List<Libro> findTop5ByOrderByDescargasDesc();
    List<Libro> findAllByOrderByAutorNombreAsc();
    List<Libro> findAllByOrderByIdiomaAsc();
    @Query("SELECT l FROM Libro l JOIN l.autor a WHERE LOWER(a.nombre) LIKE LOWER (CONCAT('%', :nombreAutor, '%'))")
    List<Libro> librosPorNombreDeAutor(String nombreAutor);
    @Query("SELECT l FROM Libro l WHERE LOWER(l.idioma) LIKE LOWER (CONCAT('%', :idioma, '%'))")
    List<Libro> librosPorIdiomaSeleccionado(String idioma);
}
