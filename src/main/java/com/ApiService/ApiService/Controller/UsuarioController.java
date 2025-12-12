package com.ApiService.ApiService.Controller;

import com.ApiService.ApiService.Entity.Result;
import com.ApiService.ApiService.Entity.Usuario;
import com.ApiService.ApiService.Service.LogicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Api Usuario", description = "Controlador para operaciones CRUD de Usuario")
@RestController
@RequestMapping("/api/")
public class UsuarioController {

    @Autowired
    private LogicService servicioUsuario;

    public UsuarioController(LogicService servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Devuelve una lista de todos los usuarios registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "No hay usuarios por mostrar", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<Result> GetAll(@RequestParam(required = false) String filtroPor) {
        try {
            Result resultado = servicioUsuario.GetAll(filtroPor);
            return ResponseEntity.status(resultado.status).body(resultado);
        } catch (Exception ex) {
            Result errorRespuesta = new Result();
            errorRespuesta.correct = false;
            errorRespuesta.status = 500;
            errorRespuesta.errorMessage = "Ocurrió un error: " + ex.getMessage();
            return ResponseEntity.status(errorRespuesta.status).body(errorRespuesta);
        }
    }

    @PostMapping("add")
    @Operation(
            summary = "Agregar usuario",
            description = "Registra un nuevo usuario con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario agregado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o duplicados",
                    content = @Content(examples = @ExampleObject(
                            value = "{\"correct\": false, \"status\": 400, \"errorMessage\": \"Email vacío\"}"
                    ))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<Result> Add(@RequestBody Usuario usuario) {
        try {
            Result resultado = servicioUsuario.Add(usuario);
            return ResponseEntity.status(resultado.status).body(resultado);
        } catch (Exception ex) {
            Result errorRespuesta = new Result();
            errorRespuesta.correct = false;
            errorRespuesta.status = 500;
            errorRespuesta.errorMessage = "Ocurrió un error: " + ex.getMessage();
            return ResponseEntity.status(errorRespuesta.status).body(errorRespuesta);
        }
    }

    @PutMapping("update/{id}")
    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza todos los campos del usuario identificado por el ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<Result> Update(@PathVariable String id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            Result resultado = servicioUsuario.Update(usuario);
            return ResponseEntity.status(resultado.status).body(resultado);
        } catch (Exception ex) {
            Result errorRespuesta = new Result();
            errorRespuesta.correct = false;
            errorRespuesta.status = 500;
            errorRespuesta.errorMessage = "Ocurrió un error: " + ex.getMessage();
            return ResponseEntity.status(errorRespuesta.status).body(errorRespuesta);
        }
    }

    @PatchMapping("patch/{id}")
    @Operation(
            summary = "Actualizar parcialmente al usuario",
            description = "Modifica solo los campos enviados en la petición"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado parcialmente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<Result> Patch(@PathVariable String id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            Result resultado = servicioUsuario.Patch(usuario);
            return ResponseEntity.status(resultado.status).body(resultado);
        } catch (Exception ex) {
            Result errorRespuesta = new Result();
            errorRespuesta.correct = false;
            errorRespuesta.status = 500;
            errorRespuesta.errorMessage = "Ocurrió un error: " + ex.getMessage();
            return ResponseEntity.status(errorRespuesta.status).body(errorRespuesta);
        }
    }

    @DeleteMapping("delete/{id}")
    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario de la lista mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<Result> Delete(@PathVariable String id) {
        try {
            Result resultado = servicioUsuario.Delete(id);
            return ResponseEntity.status(resultado.status).body(resultado);
        } catch (Exception ex) {
            Result errorRespuesta = new Result();
            errorRespuesta.correct = false;
            errorRespuesta.status = 500;
            errorRespuesta.errorMessage = "Ocurrió un error: " + ex.getMessage();
            return ResponseEntity.status(errorRespuesta.status).body(errorRespuesta);
        }
    }
}
