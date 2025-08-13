package com.oraclenextone.aluracursos.literalura.menus;

public class Menu {
    public static void menuInicial() {
        System.out.println("""
                ******************************************************
                 **** Digite el numero de la opcion deseada ****
                1) Buscar Libro en Gutendex API
                2) Buscar libro por Titulo en la Base de Datos 
                3) Buscar Libro por Autor en la Base de Datos
                4) Buscar Libros por Idioma en la Base de Datos
                5) Mostrar lista de Libros almacenados
                0) Salir
                ******************************************************
                """);
    }

    public static void menuBusquedaLista(){
        System.out.println("""
                ******************************************************
                 **** Digite el numero de la opcion deseada ****
                 
                OBTENER LA LISTA DE LIBROS POR:
                1) Titulos
                2) Autores
                3) Autores Vivos en un determinado año
                4) Idioma
                5) Top 5 Libros mas descargados
                0) Salir
                ******************************************************
                """);
    }
}
