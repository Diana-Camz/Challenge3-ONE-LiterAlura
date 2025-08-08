package com.oraclenextone.aluracursos.literalura.principal;

import com.oraclenextone.aluracursos.literalura.modelos.DatosGenerales;
import com.oraclenextone.aluracursos.literalura.services.ConsultaApi;
import com.oraclenextone.aluracursos.literalura.services.Conversor;

import java.util.Scanner;

public class Principal {
    ConsultaApi consultaApi = new ConsultaApi();
    Conversor conversor = new Conversor();
    //Scanner teclado = new Scanner(System.in);
    private static final String BASE_URL = "https://gutendex.com/books/";

    public void muestraDatos(){
        var json = consultaApi.obtenerDatos(BASE_URL);
        var datosSerializados = conversor.obtenerDatos(json, DatosGenerales.class);
        System.out.println(datosSerializados);
    }
}
