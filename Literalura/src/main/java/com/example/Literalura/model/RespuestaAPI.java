package com.example.Literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaAPI(
        int count,
        String next,
        String previous,
        List<DatosLibro> results) {
}
