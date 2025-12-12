package com.ApiService.ApiService.Service;

import com.ApiService.ApiService.Entity.Result;
import com.ApiService.ApiService.Entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class LogicService {

    private final List<Usuario> usuarios = new ArrayList<>();

    private String Validacion(Usuario usuario) {

        if (usuario == null) {
            return "Usuario nulo";
        }

        if (usuario.getName() == null || usuario.getName().trim().isEmpty()) {
            return "Name vacío";
        }
        if (!usuario.getName().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            return "Name solo debe contener letras";
        }

        if (usuario.getId() == null || usuario.getId().trim().isEmpty()) {
            return "ID vacío";
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            return "Email vacío";
        }
        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Email inválido";
        }

        if (usuario.getPhone() == null || usuario.getPhone().isEmpty()) {
            return "Phone vacío";
        }

        boolean telefonoInvalido = usuario.getPhone().stream()
                .anyMatch(telefono -> telefono == null || !telefono.matches("^\\d{10}$"));

        if (telefonoInvalido) {
            return "Teléfono debe tener 10 dígitos numéricos";
        }

        return null;
    }

    private String DuplicadoAdd(Usuario usuario) {

        boolean idDuplicado = usuarios.stream()
                .anyMatch(usuarioExiste -> usuarioExiste.getId().equals(usuario.getId()));
        if (idDuplicado) {
            return "ID ya existe";
        }

        boolean emailDuplicado = usuarios.stream()
                .anyMatch(usuarioExiste -> usuarioExiste.getEmail().equalsIgnoreCase(usuario.getEmail()));
        if (emailDuplicado) {
            return "Email ya existe";
        }

        boolean phoneDuplicado = usuarios.stream()
                .flatMap(usuarioExiste -> usuarioExiste.getPhone().stream())
                .anyMatch(telefono -> usuario.getPhone().contains(telefono));
        if (phoneDuplicado) {
            return "Teléfono ya existe";
        }

        return null;
    }

    private String DuplicadoUpdate(Usuario usuario) {

        for (Usuario usuarioUpdate : usuarios) {

            if (usuarioUpdate.getId().equals(usuario.getId())) {
                continue;
            }

            if (usuarioUpdate.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                return "Email ya existe";
            }

            boolean duplicadoPhone = usuarioUpdate.getPhone().stream()
                    .anyMatch(telefono -> usuario.getPhone().contains(telefono));
            if (duplicadoPhone) {
                return "Teléfono ya existe";
            }
        }

        return null;
    }

    private Usuario UsuarioById(String id) {
        return usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Result GetAll(String orderBy) {

        Result result = new Result();

        if (usuarios.isEmpty()) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = "No hay usuarios por mostrar";
            return result;
        }

        if (orderBy == null || orderBy.trim().isEmpty()) {
            result.correct = true;
            result.status = 200;
            result.Object = usuarios;
            return result;
        }

        try {
            String orden = orderBy.trim().toLowerCase();

            List<Usuario> ordenados = usuarios.stream()
                    .sorted((usuario1, usuario2) -> {
                        return switch (orden) {
                            case "id" -> usuario1.getId().compareTo(usuario2.getId());
                            case "name" -> usuario1.getName().compareToIgnoreCase(usuario2.getName());
                            case "email" -> usuario1.getEmail().compareToIgnoreCase(usuario2.getEmail());
                            case "phone" -> usuario1.getPhone().get(0).compareTo(usuario2.getPhone().get(0));
                            default -> 0;
                        };
                    })
                    .toList();

            result.correct = true;
            result.status = 200;
            result.Object = ordenados;

        } catch (Exception ex) {
            result.correct = false;
            result.status = 500;
            result.errorMessage = "Ocurrió un error: " + ex.getMessage();
        }

        return result;
    }

    public Result Add(Usuario usuario) {

        Result result = new Result();
        usuario.setId(UUID.randomUUID().toString());

        String err = Validacion(usuario);
        if (err != null) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = err;
            return result;
        }

        err = DuplicadoAdd(usuario);
        if (err != null) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = err;
            return result;
        }

        usuarios.add(usuario);
        result.correct = true;
        result.status = 200;
        result.Object = usuario;

        return result;
    }

    public Result Update(Usuario usuario) {

        Result result = new Result();

        if (usuario == null || usuario.getId() == null) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = "Usuario nulo o sin ID";
            return result;
        }

        Usuario existe = UsuarioById(usuario.getId());
        if (existe == null) {
            result.correct = false;
            result.status = 404;
            result.errorMessage = "Usuario no encontrado";
            return result;
        }

        String err = Validacion(usuario);
        if (err != null) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = err;
            return result;
        }

        err = DuplicadoUpdate(usuario);
        if (err != null) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = err;
            return result;
        }

        existe.setName(usuario.getName());
        existe.setEmail(usuario.getEmail());
        existe.setPhone(usuario.getPhone());

        result.correct = true;
        result.status = 200;
        result.Object = existe;

        return result;
    }

    public Result Patch(Usuario usuario) {

        Result result = new Result();

        if (usuario == null || usuario.getId() == null) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = "Usuario nulo o sin ID";
            return result;
        }

        Usuario existe = UsuarioById(usuario.getId());
        if (existe == null) {
            result.correct = false;
            result.status = 404;
            result.errorMessage = "Usuario no encontrado";
            return result;
        }

        if (usuario.getName() != null && !usuario.getName().isBlank()) {
            if (!usuario.getName().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
                result.correct = false;
                result.status = 400;
                result.errorMessage = "Name solo debe contener letras";
                return result;
            }
            existe.setName(usuario.getName());
        }

        if (usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
            if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                result.correct = false;
                result.status = 400;
                result.errorMessage = "Email inválido";
                return result;
            }
            existe.setEmail(usuario.getEmail());
        }

        if (usuario.getPhone() != null && !usuario.getPhone().isEmpty()) {

            boolean telefonoInvalido = usuario.getPhone().stream()
                    .anyMatch(telefono -> telefono == null || !telefono.matches("^\\d{10}$"));

            if (telefonoInvalido) {
                result.correct = false;
                result.status = 400;
                result.errorMessage = "Teléfono debe tener 10 dígitos numéricos";
                return result;
            }

            existe.setPhone(usuario.getPhone());
        }

        String err = DuplicadoUpdate(existe);
        if (err != null) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = err;
            return result;
        }

        result.correct = true;
        result.status = 200;
        result.Object = existe;

        return result;
    }

    public Result Delete(String id) {

        Result result = new Result();

        if (id == null) {
            result.correct = false;
            result.status = 400;
            result.errorMessage = "Sin campo Id";
            return result;
        }

        Usuario existe = UsuarioById(id);
        if (existe == null) {
            result.correct = false;
            result.status = 404;
            result.errorMessage = "Usuario no encontrado";
            return result;
        }

        usuarios.remove(existe);
        result.correct = true;
        result.status = 200;
        result.Object = existe;

        return result;
    }
}
