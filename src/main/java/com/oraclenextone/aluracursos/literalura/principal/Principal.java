package com.oraclenextone.aluracursos.literalura.principal;

import com.oraclenextone.aluracursos.literalura.menus.Menu;
import com.oraclenextone.aluracursos.literalura.modelos.*;
import com.oraclenextone.aluracursos.literalura.repository.AutorRepository;
import com.oraclenextone.aluracursos.literalura.repository.LibroRepository;
import com.oraclenextone.aluracursos.literalura.services.ConsultaApi;
import com.oraclenextone.aluracursos.literalura.services.Conversor;

import java.util.*;

public class Principal {
    private final ConsultaApi consultaApi = new ConsultaApi();
    private final Conversor conversor = new Conversor();


    private static final String BASE_URL = "https://gutendex.com/books/";
    List<Libro> listaLibros;
    private LibroRepository repositoryL;
    private AutorRepository repositoryA;
    Scanner teclado = new Scanner(System.in);
    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositoryL = repository;
        this.repositoryA = autorRepository;
    }


    public void aplicacionLiteralura(){
        boolean salir = false;
        System.out.println("*** BIENVENIDO A LITERALURA ***");

        do{
            Menu.menuInicial();
            Scanner teclado = new Scanner(System.in);
            if(teclado.hasNextInt()){
                int opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion){
                    case 1: {
                        getDatosLibroApiGutendex();
                        break;
                    }
                    case 2: {
                        buscarLibroPorTituloEnBD();
                        break;
                    }
                    case 3: {
                        buscarLibroPorAutorEnBD();
                        break;
                    }
                    case 4:{
                        buscarLibroPorIdiomaEnBD();
                        break;
                    }
                    case 5: {
                        boolean salirDeMenuDeListas = false;
                        do {
                            System.out.println("\n******************************************************");
                            System.out.println("MOSTRAR LISTAS DE LIBROS ALMACENADOS");
                            Menu.menuBusquedaLista();
                            if (teclado.hasNextInt()){
                                int opcionLista = teclado.nextInt();
                                switch (opcionLista){
                                    case 1: {
                                        System.out.println("VER LIBROS POR TITULO:");
                                        listaLibrosPorTitulo();
                                        break;
                                    }
                                    case 2: {
                                        System.out.println("VER LIBROS POR AUTOR");
                                        listaLibrosPorAutor();
                                        break;
                                    }
                                    case 3: {
                                        System.out.println("""
                                                "VER LIBROS POR AUTORES VIVOS EN UN DETERMINADO AÑO"
                                                "************ POR FAVOR INSERTA EL AÑO ************"
                                                """);
                                        if (teclado.hasNextInt()){
                                            int anio = teclado.nextInt();
                                            teclado.nextLine();
                                            listaLibrosPorAnio(anio);
                                        }else{
                                            System.out.println("Porfavor, ingresa una fecha valida");
                                        }
                                        break;
                                    }
                                    case 4: {
                                        System.out.println("VER LIBROS POR IDIOMA");
                                        listaLibroPorIdiomas();
                                        break;
                                    }
                                    case 5: {
                                        System.out.println("TOP 5 LIBROS MAS DESCARGADOS");
                                        top5LibrosMasDescargados();
                                        break;
                                    }
                                    case 0: {
                                        salirDeMenuDeListas = true;
                                        break;
                                    }
                                    default: {
                                        System.out.println("Porfavor, ingresa una opcion valida");
                                    }
                                }

                            }else{
                                System.out.println("Porfavor, ingresa una opcion valida");
                            }
                        } while(!salirDeMenuDeListas);
                        break;
                    }
                    case 0: {
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

    private void getDatosLibroApiGutendex(){
        System.out.println("""
                ****************** BUSCAR LIBRO EN GUTENDEX API *******************
                *** POR FAVOR ESCRIBE EL TITULO DEL LIBRO QUE QUIERES ENCONTRAR ***
                """);
        String tituloTeclado = teclado.nextLine();
        var json = consultaApi.obtenerDatos(BASE_URL + "?search=" + tituloTeclado.replace(" ", "%20"));
        System.out.println(json);
        DatosGenerales jsonSerializado = conversor.obtenerDatos(json, DatosGenerales.class);
        System.out.println(jsonSerializado);

        Optional <DatosLibro> libroBuscado = jsonSerializado.resultados().stream()
                .filter(l -> l.titulo().toLowerCase().contains(tituloTeclado.toLowerCase()))
                .findFirst();

        if (libroBuscado.isPresent()){
            DatosLibro libroEncontrado = libroBuscado.get();
            //listasLibros.add(libroEncontrado);
            Autor autor = null;
            if (!libroEncontrado.autor().isEmpty() && libroEncontrado.autor() != null){
                String autorNombre = libroEncontrado.autor().get(0).nombre();
                autor = repositoryA.findByNombreIgnoreCase(autorNombre).orElseGet(() -> repositoryA.save(new Autor(libroEncontrado.autor().get(0))));
            }else{
                autor = repositoryA.findByNombreIgnoreCase("Sin Nombre")
                        .orElseGet(() -> repositoryA.save(new Autor(
                                new DatosAutor("Sin Nombre", 0, 0)
                        )));
            }
            Libro libro = new Libro(libroEncontrado);
            libro.setAutor(autor);
            repositoryL.save(libro);
            System.out.println("LIBRO ENCONTRADO EN GUTENDEX: \n" +
                    libroEncontrado.toString());
        }else{
            System.out.println("NO FUE POSIBLE ENCONTRAR EL LIBRO EN LA API GUTENDEX");
            System.out.println("*** ASEGURATE DE ESCRIBIR EL TITULO COMPLETO Y CORRECTO DEL LIBRO ***");
        }
    }

    private void buscarLibroPorTituloEnBD(){
        System.out.println("""
                *********** BUSCAR LIBRO POR TITULO EN LA BASE DE DATOS ***********
                *** POR FAVOR ESCRIBE EL TITULO DEL LIBRO QUE QUIERES ENCONTRAR ***
                """);
        String tituloTeclado = teclado.nextLine();

        Optional libro = repositoryL.findByTituloContainsIgnoreCase(tituloTeclado);
        /*Optional<Libro> libro = listaLibros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(tituloTeclado.toLowerCase()))
                .findFirst();*/

        if (libro.isPresent()){
            System.out.println("LIBRO ENCONTRADO EN LA BASE DE DATOS" + libro.get().toString());
        }else{
            System.out.println("NO FUE POSIBLE ENCONTRAR EL LIBRO POR EL TITULO QUE ESCRIBISTE");
        }

    }

    private void buscarLibroPorAutorEnBD(){
        System.out.println("""
                *********** BUSCAR LIBRO POR AUTOR EN LA BASE DE DATOS ***********
                *** POR FAVOR ESCRIBE EL AUTOR DEL LIBRO QUE QUIERES ENCONTRAR ***
                """);
        String nombreAutor = teclado.nextLine();
        List<Libro> librosPorAutor = repositoryL.librosPorNombreDeAutor(nombreAutor);

        if (librosPorAutor.isEmpty()) {
            System.out.println("NO FUE POSIBLE ENCONTRAR EL LIBRO POR EL AUTOR QUE ESCRIBISTE");
        } else {
            librosPorAutor.forEach(System.out::println);
        }
    }

    private void buscarLibroPorIdiomaEnBD(){
        System.out.println("""
                *********** BUSCAR LIBRO POR IDIOMA EN LA BASE DE DATOS ***********
                *** POR FAVOR ESCRIBE EL CODIGO IDIOMA DEL LIBRO QUE QUIERES ENCONTRAR ***
                - ESPAÑOL -> es
                - INGLES -> en
                - ITALIANO -> it
                - PORTUGES -> pt
                - FRANCES -> fr
                """);
        String idioma = teclado.nextLine();
        List<Libro> listaLibros = repositoryL.librosPorIdiomaSeleccionado(idioma);

        if (listaLibros.isEmpty()){
            System.out.println("No fue posible encontrar lista de libros por ese Idioma");
        } else {
            listaLibros.forEach(l -> System.out.println("\n *****************************" +
                    "\n-TITULO: " + l.getTitulo() +
                    "\n-IDIOMA: " + l.getIdioma()
            ));
        }
    }

    private void listaLibrosPorTitulo(){
        listaLibros = repositoryL.findAll();
        listaLibros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(l -> System.out.println("\n *****************************" +
                        "\n-TITULO: " + l.getTitulo()
                ));
    }

    private void listaLibrosPorAutor(){
        List<Libro> librosPorAutor = repositoryL.findAllByOrderByAutorNombreAsc();

            librosPorAutor.forEach(l -> {
                String nombreAutor = (l.getAutor() != null) ? l.getAutor().getNombre() : "Sin Nombre";
                System.out.println("\n *****************************" +
                        "\n-TITULO: " + l.getTitulo() +
                        "\n-AUTOR: " + nombreAutor);
            });
    }

    private void listaLibroPorIdiomas(){
        List<Libro> listaLibros = repositoryL.findAllByOrderByIdiomaAsc();

        if (listaLibros.isEmpty()){
            System.out.println("No fue posible encontrar lista de libros por Idioma");
        } else {
            listaLibros.forEach(l -> System.out.println("\n *****************************" +
                    "\n-TITULO: " + l.getTitulo() +
                    "\n-IDIOMA: " + l.getIdioma()
            ));
        }

    }

    private void listaLibrosPorAnio(int anio){
        List<Autor> librosPorAutoresVivos = repositoryA.findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(anio, anio);

       if(librosPorAutoresVivos.isEmpty()){
           System.out.println("Autores no encontrados en ese año, o ingresaste una fecha erronea");
       }else{
           librosPorAutoresVivos.forEach(a -> a.getLibro().forEach( l -> System.out.println("\n *****************************" +
                   "\n-TITULO: " + l.getTitulo() +
                   "\n-AUTOR: " + a.getNombre() +
                   "\n FECHA DE NACIMIENTO: " + a.getFechaNacimiento() +
                   "\n FECHA DE FALLECIMIENTO: " + a.getFechaFallecimiento()
           )));
       }
    }

    private void top5LibrosMasDescargados(){
        List<Libro> top5Libros = repositoryL.findTop5ByOrderByDescargasDesc();
        top5Libros.forEach(l -> System.out.println("\n *****************************" +
                "\n-TITULO: " + l.getTitulo() +
                "\n-DESCARGAS: " + l.getDescargas()
        ));
    }

}
