package com.oraclenextone.aluracursos.literalura.modelos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    @ManyToOne()
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idioma;
    private Integer descargas;

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas().get(0);
        this.descargas = datosLibro.descargas();

    }

    public Libro() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
            return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "\n***********************************************" +
                "\n*** lIBRO: ***" +
                "\n-TITULO: '" + titulo + '\'' +
                "\n-AUTOR: " + autor +
                "\n-IDIOMA: " + idioma +
                "\n-DESCARGAS: " + descargas +
                "\n***********************************************";
    }
}
