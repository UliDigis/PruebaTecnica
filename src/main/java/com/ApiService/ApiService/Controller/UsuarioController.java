package com.ApiService.ApiService.Controller;

import com.ApiService.ApiService.Entity.Result;
import com.ApiService.ApiService.Entity.Usuario;
import com.ApiService.ApiService.Service.LogicService;
import jakarta.websocket.server.PathParam;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UsuarioController {

    @Autowired
    private LogicService logicService;

    public UsuarioController(LogicService logicService) {
        this.logicService = logicService;
    }

    @GetMapping
    public Result GetAll(@RequestParam(required = false) String filtroBy) {
        return logicService.GetAll(filtroBy); 
    }

    @PostMapping("add")
    public Result Add(@RequestBody Usuario usuario) {
        UUID generador = UUID.randomUUID();
        usuario.setId(generador.toString());
        return logicService.Add(usuario);
    }

    @PutMapping("update/{id}")
    public Result Update(@PathVariable String id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return logicService.Update(usuario);
    }

    @PatchMapping("patch/{id}")
    public Result Patch(@PathVariable String id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return logicService.Patch(usuario);
    }

    @DeleteMapping("delete/{id}")
    public Result Delete(@PathVariable String id) {
        return logicService.Delete(id);
    }

}
