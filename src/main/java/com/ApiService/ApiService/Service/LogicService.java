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

    public Result GetAll() {
        Result result = new Result();
        try {

            if (usuarios.isEmpty()) {
                result.errorMessage = "No hay ningun usuario por mostar";
                result.correct = false;
                result.status = 400;
            } else {
                result.correct = true;
                result.status = 200;
                result.Object = usuarios;
            }

        } catch (Exception ex) {
            result.status = 500;
            result.errorMessage = ex.getMessage();
        }
        return result;
    }

    public Result Add(Usuario usuario) {
        Result result = new Result();
        try {

            if (usuario == null) {
                result.correct = false;
                result.status = 400;
                result.errorMessage = "El usuario esta vacio";
                
            } else {
                usuarios.add(usuario);
                result.correct = true;
                result.status = 200;
                result.Object = usuario;
            }

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

            if (usuario == null) {
                result.correct = false;
                result.errorMessage = "El usuario viene vacio";
                result.status = 400;
            } else {

                Usuario usuarioExiste = usuarios.stream()
                        .filter(usuarioF -> usuarioF.getId().equals(usuario.getId()))
                        .findFirst()
                        .orElse(null);

                if (usuarioExiste == null) {
                    result.correct = false;
                    result.errorMessage = "Usuario no encontrado";
                    result.status = 404;
                } else {
                    usuarioExiste.setName(usuario.getName());
                    usuarioExiste.setEmail(usuario.getEmail());
                    usuarioExiste.setPhone(usuario.getPhone());

                    result.correct = true;
                    result.status = 200;
                    result.Object = usuarioExiste;
                }
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.status = 500;
        }

        return result;
    }

    public Result Patch(Usuario usuario) {
        Result result = new Result();

        try {

            if (usuario == null) {
                result.correct = false;
                result.errorMessage = "El usuario viene vacio";
                result.status = 400;
            } else {

                Usuario usuarioExiste = usuarios.stream()
                        .filter(usuarioF -> usuarioF.getId().equals(usuario.getId()))
                        .findFirst()
                        .orElse(null);

                if (usuarioExiste == null) {
                    result.correct = false;
                    result.errorMessage = "Usuario no encontrado";
                    result.status = 404;
                } else {
                    if (usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
                        usuarioExiste.setEmail(usuario.getEmail());
                    }
                    if (usuario.getName() != null && !usuario.getName().isBlank()) {
                        usuarioExiste.setName(usuario.getName());
                    }
                    if (usuario.getPhone() != null && !usuario.getPhone().isEmpty()) {
                        usuarioExiste.setPhone(usuario.getPhone());
                    }

                    result.correct = true;
                    result.status = 200;
                    result.Object = usuarioExiste;
                }

            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.status = 500;
        }

        return result;
    }

    public Result Delete(UUID id) {
        Result result = new Result();
        
        try{
            
            if(id == null){
                result.correct = false;
                result.errorMessage = "Sin campo Id";
                result.status = 400;
            }else{
                
                Usuario usuarioExiste = usuarios.stream()
                        .filter(usuarioF -> usuarioF.getId().equals(id))
                        .findFirst()
                        .orElse(null);
                
                if(usuarioExiste == null){
                    result.correct = false;
                    result.errorMessage = "Usuario no encontrado";
                    result.status = 404;
                }else{
                    usuarios.remove(usuarioExiste);
                    
                    result.correct = true;
                    result.status = 200;
                    result.Object = "Usuario eliminado"+usuarioExiste;
                }
            }
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.status = 500;
        }
        
        return result;
    }

}
