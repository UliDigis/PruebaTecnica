package com.ApiService.ApiService.Service;

import com.ApiService.ApiService.Entity.Result;
import com.ApiService.ApiService.Entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LogicService {

    private final List<Usuario> usuarios = new ArrayList<>();

//    Validaciones de campos
    private String Validacion(Usuario usuario) {
        if (usuario == null) {
            return "Usuario nulo";
        }
        if (usuario.getId() == null || usuario.getId().trim().isEmpty()) {
            return "ID vacío";
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            return "Email vacío";
        }
        if (usuario.getPhone() == null || usuario.getPhone().isEmpty()) {
            return "Debe ingresar teléfonos";
        }
        boolean vacios = usuario.getPhone().stream().anyMatch(t -> t == null || t.trim().isEmpty());
        if (vacios) {
            return "Un teléfono está vacío";
        }
        return null;
    }

    private String DuplicadoAdd(Usuario usuario) {
        boolean idDuplicado = usuarios.stream().anyMatch(usuarioExiste -> usuarioExiste.getId().equals(usuario.getId()));
        if (idDuplicado) {
            return "ID ya existe";
        }
        boolean emailDuplicado = usuarios.stream().anyMatch(usuarioExiste -> usuarioExiste.getEmail().equalsIgnoreCase(usuario.getEmail()));
        if (emailDuplicado) {
            return "Email ya existe";
        }
        boolean phoneDuplicado = usuarios.stream().flatMap(usuarioExiste -> usuarioExiste.getPhone().stream())
                .anyMatch(t -> usuario.getPhone().contains(t));
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
            boolean duplicadoPhone = usuarioUpdate.getPhone().stream().anyMatch(t -> usuario.getPhone().contains(t));
            if (duplicadoPhone) {
                return "Teléfono ya existe";
            }
        }
        return null;
    }
//    Validaciones de campos


//    Obtener Usuario por su ID
    private Usuario UsuarioById(String id) {
        return usuarios.stream().filter(usuario -> usuario.getId().equals(id)).findFirst().orElse(null);
    }

    public Result GetAll(String orderBy) {
        Result result = new Result();

        try {
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

            String orden = orderBy.trim().toLowerCase();

            List<Usuario> ordenados = usuarios.stream()
                    .sorted((usuario1, usuario2) -> {
                        if (orden.equals("id")) {
                            String result1 = usuario1.getId() == null ? "" : usuario1.getId();
                            String result2 = usuario2.getId() == null ? "" : usuario2.getId();
                            return result1.compareTo(result2);
                        }
                        if (orden.equals("name")) {
                            String result1 = usuario1.getName() == null ? "" : usuario1.getName().toLowerCase();
                            String result2 = usuario2.getName() == null ? "" : usuario2.getName().toLowerCase();
                            return result1.compareTo(result2);
                        }
                        if (orden.equals("email")) {
                            String result1 = usuario1.getEmail() == null ? "" : usuario1.getEmail().toLowerCase();
                            String result2 = usuario2.getEmail() == null ? "" : usuario2.getEmail().toLowerCase();
                            return result1.compareTo(result2);
                        }
                        if (orden.equals("phone")) {
                            String result1 = "";
                            String result2 = "";
                            if (usuario1.getPhone() != null && !usuario1.getPhone().isEmpty()) {
                                result1 = usuario1.getPhone().get(0);
                            }
                            if (usuario2.getPhone() != null && !usuario2.getPhone().isEmpty()) {
                                result2 = usuario2.getPhone().get(0);
                            }
                            return result1.compareTo(result2);
                        }
                        return 0;
                    })
                    .toList();

            result.correct = true;
            result.status = 200;
            result.Object = ordenados;

        } catch (Exception ex) {
            result.correct = false;
            result.status = 500;
            result.errorMessage = ex.getMessage();
        }
        return result;
    }

    public Result Add(Usuario usuario) {
        Result result = new Result();

        try {
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

        } catch (Exception ex) {
            result.correct = false;
            result.status = 500;
            result.errorMessage = ex.getMessage();
        }

        return result;
    }

    public Result Update(Usuario usuario) {
        Result result = new Result();

        try {
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

        } catch (Exception ex) {
            result.correct = false;
            result.status = 500;
            result.errorMessage = ex.getMessage();
        }
        return result;
    }

    public Result Patch(Usuario usuario) {
        Result result = new Result();

        try {
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

            if (usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
                existe.setEmail(usuario.getEmail());
            }

            if (usuario.getName() != null && !usuario.getName().isBlank()) {
                existe.setName(usuario.getName());
            }

            if (usuario.getPhone() != null && !usuario.getPhone().isEmpty()) {
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

        } catch (Exception ex) {
            result.correct = false;
            result.status = 500;
            result.errorMessage = ex.getMessage();
        }
        return result;
    }

    public Result Delete(String id) {
        Result result = new Result();

        try {
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

        } catch (Exception ex) {
            result.correct = false;
            result.status = 500;
            result.errorMessage = ex.getMessage();
        }

        return result;
    }
}
