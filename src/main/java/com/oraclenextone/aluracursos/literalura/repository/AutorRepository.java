package com.oraclenextone.aluracursos.literalura.repository;

import com.oraclenextone.aluracursos.literalura.modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombre);
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(
            Integer anio1, Integer anio2);
}
