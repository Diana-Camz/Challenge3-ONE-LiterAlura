package com.oraclenextone.aluracursos.literalura.services;

public interface IConversor {
    <T> T obtenerDatos(String json, Class<T> clase);
}
