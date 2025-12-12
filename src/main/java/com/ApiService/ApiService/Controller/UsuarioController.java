package com.ApiService.ApiService.Controller;

import com.ApiService.ApiService.Entity.Result;
import com.ApiService.ApiService.Entity.Usuario;
import com.ApiService.ApiService.Service.LogicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuarios", description = "Endpoints para operaciones CRUD de usuarios.")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final LogicService servicioUsuario;

    public UsuarioController(LogicService servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping
    @Operation(
            summary = "Listar usuarios",
            description = "Devuelve la lista de usuarios. Opcionalmente permite ordenar con el parámetro orderBy."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    value = """
                                            {
                                              "correct": true,
                                              "status": 200,
                                              "errorMessage": null,
                                              "Object": [
                                                {
                                                  "id": "2a1d4b6f-1c7e-4d5b-9b8f-2ddc9a1f8c10",
                                                  "name": "Ulises",
                                                  "email": "ulises@gmail.com",
                                                  "phone": ["5512345678"]
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No hay usuarios por mostrar",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Sin usuarios",
                                    value = """
                                            {
                                              "correct": false,
                                              "status": 400,
                                              "errorMessage": "No hay usuarios por mostrar",
                                              "Object": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Error interno",
                                    value = """
                                            {
                                              "correct": false,
                                              "status": 500,
                                              "errorMessage": "Ocurrió un error: <detalle>",
                                              "Object": null
                                            }
                                            """
                            )
                    )
            )
    })
    public ResponseEntity<Result> GetAll(
            @Parameter(
                    in = ParameterIn.QUERY,
                    name = "orderBy",
                    description = "Ordena la lista de forma ascendente por el campo indicado.",
                    required = false,
                    schema = @Schema(type = "string", allowableValues = {"id", "name", "email", "phone"})
            )
            @RequestParam(required = false) String orderBy
    ) {
        try {
            Result resultado = servicioUsuario.GetAll(orderBy);
            return ResponseEntity.status(resultado.status).body(resultado);
        } catch (Exception ex) {
            Result errorRespuesta = new Result();
            errorRespuesta.correct = false;
            errorRespuesta.status = 500;
            errorRespuesta.errorMessage = "Ocurrió un error: " + ex.getMessage();
            return ResponseEntity.status(errorRespuesta.status).body(errorRespuesta);
        }
    }

    @PostMapping
    @Operation(
            summary = "Crear usuario",
            description = "Crea un usuario nuevo. Reglas: name solo letras, email válido, phone(s) de 10 dígitos. El id se genera automáticamente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario agregado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Usuario creado",
                                    value = """
                                            {
                                              "correct": true,
                                              "status": 200,
                                              "errorMessage": null,
                                              "Object": {
                                                "id": "2a1d4b6f-1c7e-4d5b-9b8f-2ddc9a1f8c10",
                                                "name": "Kevin",
                                                "email": "kevin@gmail.com",
                                                "phone": ["5584756248", "5514006780"]
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o duplicados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Name inválido",
                                            value = """
                                                    {
                                                      "correct": false,
                                                      "status": 400,
                                                      "errorMessage": "Name solo debe contener letras",
                                                      "Object": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Email inválido",
                                            value = """
                                                    {
                                                      "correct": false,
                                                      "status": 400,
                                                      "errorMessage": "Email inválido",
                                                      "Object": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Teléfono inválido",
                                            value = """
                                                    {
                                                      "correct": false,
                                                      "status": 400,
                                                      "errorMessage": "Teléfono debe tener 10 dígitos numéricos",
                                                      "Object": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Duplicado",
                                            value = """
                                                    {
                                                      "correct": false,
                                                      "status": 400,
                                                      "errorMessage": "Email ya existe",
                                                      "Object": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Error interno",
                                    value = """
                                            {
                                              "correct": false,
                                              "status": 500,
                                              "errorMessage": "Ocurrió un error: <detalle>",
                                              "Object": null
                                            }
                                            """
                            )
                    )
            )
    })
    public ResponseEntity<Result> Add(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del usuario a crear (no incluir id).",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class),
                            examples = @ExampleObject(
                                    name = "Request de ejemplo",
                                    value = """
                                            {
                                              "name": "Kevin",
                                              "email": "kevin@gmail.com",
                                              "phone": ["5584756248", "5514006780"]
                                            }
                                            """
                            )
                    )
            )
            @RequestBody Usuario usuario
    ) {
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

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza todos los campos del usuario. Requiere enviar name, email y phone."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario actualizado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Actualización exitosa",
                                    value = """
                                            {
                                              "correct": true,
                                              "status": 200,
                                              "errorMessage": null,
                                              "Object": {
                                                "id": "2a1d4b6f-1c7e-4d5b-9b8f-2ddc9a1f8c10",
                                                "name": "Ulises",
                                                "email": "ulises12@gmail.com",
                                                "phone": ["5512345678"]
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o duplicados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Error 400",
                                    value = """
                                            {
                                              "correct": false,
                                              "status": 400,
                                              "errorMessage": "Email inválido",
                                              "Object": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "No encontrado",
                                    value = """
                                            {
                                              "correct": false,
                                              "status": 404,
                                              "errorMessage": "Usuario no encontrado",
                                              "Object": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Error interno",
                                    value = """
                                            {
                                              "correct": false,
                                              "status": 500,
                                              "errorMessage": "Ocurrió un error: <detalle>",
                                              "Object": null
                                            }
                                            """
                            )
                    )
            )
    })
    public ResponseEntity<Result> Update(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "ID del usuario a actualizar", required = true)
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos completos del usuario (name, email, phone).",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class),
                            examples = @ExampleObject(
                                    name = "Request de ejemplo",
                                    value = """
                                            {
                                              "name": "Ulises",
                                              "email": "ulises12@gmail.com",
                                              "phone": ["5512345678"]
                                            }
                                            """
                            )
                    )
            )
            @RequestBody Usuario usuario
    ) {
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

    @PatchMapping("/{id}")
    @Operation(
            summary = "Actualizar parcialmente usuario",
            description = "Actualiza uno o más campos (name, email, phone). Solo se modifican los campos enviados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario actualizado parcialmente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Patch exitoso",
                                    value = """
                                            {
                                              "correct": true,
                                              "status": 200,
                                              "errorMessage": null,
                                              "Object": {
                                                "id": "2a1d4b6f-1c7e-4d5b-9b8f-2ddc9a1f8c10",
                                                "name": "Ulises",
                                                "email": "ulises@gmail.com",
                                                "phone": ["5512345678"]
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos o duplicados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Email inválido",
                                            value = """
                                                    {
                                                      "correct": false,
                                                      "status": 400,
                                                      "errorMessage": "Email inválido",
                                                      "Object": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Duplicado",
                                            value = """
                                                    {
                                                      "correct": false,
                                                      "status": 400,
                                                      "errorMessage": "Teléfono ya existe",
                                                      "Object": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "No encontrado",
                                    value = """
                                            {
                                              "correct": false,
                                              "status": 404,
                                              "errorMessage": "Usuario no encontrado",
                                              "Object": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Error interno",
                                    value = """
                                            {
                                              "correct": false,
                                              "status": 500,
                                              "errorMessage": "Ocurrió un error: <detalle>",
                                              "Object": null
                                            }
                                            """
                            )
                    )
            )
    })
    public ResponseEntity<Result> Patch(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "ID del usuario a modificar", required = true)
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Enviar solo los campos a modificar (name/email/phone).",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Actualizar name",
                                            value = """
                                                    {
                                                      "name": "Ulises"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Actualizar email",
                                            value = """
                                                    {
                                                      "email": "ulises12@gmail.com"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Actualizar phone",
                                            value = """
                                                    {
                                                      "phone": ["5512345678"]
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @RequestBody Usuario usuario
    ) {
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

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario por ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario eliminado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Eliminación exitosa",
                                    value = """
                                            {
                                              "correct": true,
                                              "status": 200,
                                              "errorMessage": null,
                                              "Object": {
                                                "id": "2a1d4b6f-1c7e-4d5b-9b8f-2ddc9a1f8c10",
                                                "name": "Ulises",
                                                "email": "ulises@gmail.com",
                                                "phone": ["5512345678"]
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "No encontrado",
                                    value = """
                                            {
                                              "correct": false,
                                              "status": 404,
                                              "errorMessage": "Usuario no encontrado",
                                              "Object": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "Error interno",
                                    value = """
                                            {
                                              "correct": false,
                                              "status": 500,
                                              "errorMessage": "Ocurrió un error: <detalle>",
                                              "Object": null
                                            }
                                            """
                            )
                    )
            )
    })
    public ResponseEntity<Result> Delete(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "ID del usuario a eliminar", required = true)
            @PathVariable String id
    ) {
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
