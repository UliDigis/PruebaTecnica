package com.ApiService.ApiService.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "Usuario", description = "Entidad que representa a un usuario registrado")
public class Usuario {

    @Schema(description = "Identificador único del usuario", example = "a3f8e1bc-1234-4bde-9a3b-d4f5b9912c22")
    private String id;

    @Schema(description = "Nombre completo del usuario", example = "Ulises Muñoz")
    private String name;

    @Schema(description = "Correo electrónico del usuario", example = "uliMunoz@example.com")
    private String email;

    @Schema(description = "Lista de teléfonos del usuario", example = "[\"5523419087\", \"5567812045\"]")
    private List<String> phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }
}
