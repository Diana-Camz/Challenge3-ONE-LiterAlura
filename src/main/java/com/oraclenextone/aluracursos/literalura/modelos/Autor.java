package com.oraclenextone.aluracursos.literalura.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {}

    public Autor(DatosAutor a) {
            this.nombre = a.nombre();
            this.fechaNacimiento = a.fechaNacimiento();
            this.fechaFallecimiento = a.fechaFallecimiento();
    }

    @Override
    public String toString() {
        return  "\n--NOMBRE: '" + nombre + '\'' +
                "\n--FECHA DE NACIMIENTO: " + fechaNacimiento +
                "\n--FECHA DE DECESO: " + fechaFallecimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibro() {
        return libros;
    }

    public void setLibro(List<Libro> libro) {
        this.libros = libro;
    }
}
