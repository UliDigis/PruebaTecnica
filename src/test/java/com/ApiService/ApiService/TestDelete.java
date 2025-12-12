package com.ApiService.ApiService;

import com.ApiService.ApiService.Entity.Result;
import com.ApiService.ApiService.Entity.Usuario;
import com.ApiService.ApiService.Service.LogicService;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDelete {

    @Autowired
    private LogicService logicService;

    @Test
    public void DeleteOk() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("delete1@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5512345690"));

        Result resultAdd = logicService.Add(usuarioBase);

        Assertions.assertTrue(resultAdd.correct);
        Assertions.assertEquals(200, resultAdd.status);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;
        String idGuardado = usuarioGuardado.getId();

        Result resultDelete = logicService.Delete(idGuardado);

        Assertions.assertTrue(resultDelete.correct);
        Assertions.assertEquals(200, resultDelete.status);
        Assertions.assertNotNull(resultDelete.Object);

        Result resultGetAll = logicService.GetAll("");
        if (resultGetAll.correct) {
            Assertions.assertTrue(((java.util.List<Usuario>) resultGetAll.Object).stream()
                    .noneMatch(usuario -> usuario.getId().equals(idGuardado)));
        }
    }

    @Test
    public void DeleteFailNullId() {

        Result resultDelete = logicService.Delete(null);

        Assertions.assertFalse(resultDelete.correct);
        Assertions.assertEquals(400, resultDelete.status);
        Assertions.assertEquals("Sin campo Id", resultDelete.errorMessage);
    }

    @Test
    public void DeleteFailNoUser() {

        Result resultDelete = logicService.Delete("id-inexistente");

        Assertions.assertFalse(resultDelete.correct);
        Assertions.assertEquals(404, resultDelete.status);
        Assertions.assertEquals("Usuario no encontrado", resultDelete.errorMessage);
    }
}
