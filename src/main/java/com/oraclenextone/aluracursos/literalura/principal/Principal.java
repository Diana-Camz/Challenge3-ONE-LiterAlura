package com.oraclenextone.aluracursos.literalura.principal;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.oraclenextone.aluracursos.literalura.menus.Menu;
import com.oraclenextone.aluracursos.literalura.modelos.DatosGenerales;
import com.oraclenextone.aluracursos.literalura.services.ConsultaApi;
import com.oraclenextone.aluracursos.literalura.services.Conversor;

import java.util.Scanner;

public class Principal {
    private final ConsultaApi consultaApi = new ConsultaApi();
    private final Conversor conversor = new Conversor();
    private static final String BASE_URL = "https://gutendex.com/books/";


    public void aplicacionLiteralura(){
        boolean salir = false;
        System.out.println("*** BIENVENIDO A LITERALURA ***");

        do{
            Menu.menuInicial();
            Scanner teclado = new Scanner(System.in);

            if(teclado.hasNextInt()){
                int opcion = teclado.nextInt();

                switch (opcion){
                    case 1: {
                        System.out.println("Buscar Libro en Gutendex API");
                        break;
                    }
                    case 2: {
                        System.out.println("Buscar libro por Titulo en la Base de Datos");
                        break;
                    }
                    case 3: {
                        System.out.println("Buscar Libro por Autor en la Base de Datos");
                        break;
                    }
                    case 4: {
                        boolean salirDeMenuDeListas = false;
                        do {
                            System.out.println("Mostrar lista de Libros almacenados");
                            Menu.menuBusquedaLista();
                            if (teclado.hasNextInt()){
                                int opcionLista = teclado.nextInt();
                                switch (opcionLista){
                                    case 1: {
                                        System.out.println("Ver Libros por Titulos");
                                        break;
                                    }
                                    case 2: {
                                        System.out.println("Ver Libros por Autores");
                                        break;
                                    }
                                    case 3: {
                                        System.out.println("Ver Libros por Autores Vivos en un determinado ano");
                                        break;
                                    }
                                    case 4: {
                                        System.out.println("Ver Libros por Idioma");
                                        break;
                                    }
                                    case 5: {
                                        salirDeMenuDeListas = true;
                                        break;
                                    }
                                }

                            }else{
                                System.out.println("Porfavor, ingresa una opcion valida");
                            }
                        } while(!salirDeMenuDeListas);
                        break;
                    }
                    case 5: {
                        System.out.println("Gracias por usar LiterAlura. Hasta Pronto!!");
                        salir = true;
                        break;
                    }
                    default: {
                        System.out.println("Porfavor, ingresa una opcion valida");
                    }
                }
            }else{
                System.out.println("Porfavor, ingresa una opcion valida");
            }
        }while(!salir);


    }

    public void menuBusquedaPorLista(){
        System.out.println();
    }


    public void muestraDatos(){
        var json = consultaApi.obtenerDatos(BASE_URL);
        var datosSerializados = conversor.obtenerDatos(json, DatosGenerales.class);
        System.out.println(datosSerializados);
    }
}
