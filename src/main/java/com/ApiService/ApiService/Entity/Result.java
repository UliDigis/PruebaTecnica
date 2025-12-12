package com.ApiService.ApiService.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Resultado",
        description = "Objeto genérico utilizado para representar la respuesta estándar de la API")
public class Result {

    @Schema(description = "Indica si la operación fue exitosa", example = "true,false")
    public boolean correct;

    @Schema(description = "Código HTTP de la respuesta", example = "200,400,500")
    public int status;

    @Schema(description = "Mensaje descriptivo de un error (si aplica)", example = "Campo vacio, Usuario Null")
    public String errorMessage;

    @Schema(description = "Objeto devuelto por la operación, como un usuario o una lista de usuarios")
    public Object Object;
}
