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
public class TestAdd {

    @Autowired
    private LogicService logicService;

    @Test
    public void AddOk() {

        Usuario usuario = new Usuario();
        usuario.setName("Kevin");
        usuario.setEmail("kevin@gmail.com");
        usuario.setPhone(Arrays.asList("5584756248", "5514006780"));

        Result result = logicService.Add(usuario);

        Assertions.assertTrue(result.correct);
        Assertions.assertEquals(200, result.status);
        Assertions.assertNotNull(result.Object);

        Usuario usuarioAgregado = (Usuario) result.Object;
        Assertions.assertNotNull(usuarioAgregado.getId());
        Assertions.assertEquals("Kevin", usuarioAgregado.getName());
    }

    @Test
    public void AddEmailVacio() {

        Usuario usuario = new Usuario();
        usuario.setName("Kevin");
        usuario.setEmail("");
        usuario.setPhone(Arrays.asList("5584756248"));

        Result result = logicService.Add(usuario);

        Assertions.assertFalse(result.correct);
        Assertions.assertEquals(400, result.status);
        Assertions.assertEquals("Email vacío", result.errorMessage);
    }

    @Test
    public void AddEmailInvalido() {

        Usuario usuario = new Usuario();
        usuario.setName("Kevin");
        usuario.setEmail("kevin@");
        usuario.setPhone(Arrays.asList("5584756248"));

        Result result = logicService.Add(usuario);

        Assertions.assertFalse(result.correct);
        Assertions.assertEquals(400, result.status);
        Assertions.assertEquals("Email inválido", result.errorMessage);
    }

    @Test
    public void AddNameVacio() {

        Usuario usuario = new Usuario();
        usuario.setName("");
        usuario.setEmail("kevin@gmail.com");
        usuario.setPhone(Arrays.asList("5584756248"));

        Result result = logicService.Add(usuario);

        Assertions.assertFalse(result.correct);
        Assertions.assertEquals(400, result.status);
        Assertions.assertEquals("Name vacío", result.errorMessage);
    }

    @Test
    public void AddNameConNumeros() {

        Usuario usuario = new Usuario();
        usuario.setName("Kevin123");
        usuario.setEmail("kevin@gmail.com");
        usuario.setPhone(Arrays.asList("5584756248"));

        Result result = logicService.Add(usuario);

        Assertions.assertFalse(result.correct);
        Assertions.assertEquals(400, result.status);
        Assertions.assertEquals("Name solo debe contener letras", result.errorMessage);
    }

    @Test
    public void AddPhoneVacio() {

        Usuario usuario = new Usuario();
        usuario.setName("Kevin");
        usuario.setEmail("kevin@gmail.com");
        usuario.setPhone(Arrays.asList());

        Result result = logicService.Add(usuario);

        Assertions.assertFalse(result.correct);
        Assertions.assertEquals(400, result.status);
        Assertions.assertEquals("Phone vacío", result.errorMessage);
    }

    @Test
    public void AddPhoneInvalido() {

        Usuario usuario = new Usuario();
        usuario.setName("Kevin");
        usuario.setEmail("kevin@gmail.com");
        usuario.setPhone(Arrays.asList("12345abcd0"));

        Result result = logicService.Add(usuario);

        Assertions.assertFalse(result.correct);
        Assertions.assertEquals(400, result.status);
        Assertions.assertEquals("Teléfono debe tener 10 dígitos numéricos", result.errorMessage);
    }

    @Test
    public void AddPhoneDuplicado() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Carlos");
        usuarioBase.setEmail("carlos@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5566442243"));

        Result resultBase = logicService.Add(usuarioBase);
        Assertions.assertTrue(resultBase.correct);

        Usuario usuario = new Usuario();
        usuario.setName("Kevin");
        usuario.setEmail("kevin@gmail.com");
        usuario.setPhone(Arrays.asList("5566442243"));

        Result result = logicService.Add(usuario);

        Assertions.assertFalse(result.correct);
        Assertions.assertEquals(400, result.status);
        Assertions.assertEquals("Teléfono ya existe", result.errorMessage);
    }

    @Test
    public void AddEmailDuplicado() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Carlos");
        usuarioBase.setEmail("carlos@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5511111111"));

        Result resultBase = logicService.Add(usuarioBase);
        Assertions.assertTrue(resultBase.correct);

        Usuario usuario = new Usuario();
        usuario.setName("Kevin");
        usuario.setEmail("carlos@gmail.com");
        usuario.setPhone(Arrays.asList("5522222222"));

        Result result = logicService.Add(usuario);

        Assertions.assertFalse(result.correct);
        Assertions.assertEquals(400, result.status);
        Assertions.assertEquals("Email ya existe", result.errorMessage);
    }
}
