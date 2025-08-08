package com.oraclenextone.aluracursos.literalura.modelos;

public enum Idioma {
    INGLES("en"),
    ESPANOL("es"),
    FRANCES("fr"),
    PORTUGES("pt"),
    JAPONES("ja"),
    ITALIANO("it");

    private String IdiomaGutendex;
    Idioma(String idiomaGutendex){
        this.IdiomaGutendex = idiomaGutendex;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.IdiomaGutendex.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }
}
