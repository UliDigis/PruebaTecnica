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
public class TestPatch {

    @Autowired
    private LogicService logicService;

    @Test
    public void PatchNameOk() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("prueba1@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5544332211"));

        Result resultAdd = logicService.Add(usuarioBase);

        Assertions.assertTrue(resultAdd.correct);
        Assertions.assertEquals(200, resultAdd.status);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;
        String idGuardado = usuarioGuardado.getId();

        Usuario usuario = new Usuario();
        usuario.setId(idGuardado);
        usuario.setName("Ulises");

        Result resultPatch = logicService.Patch(usuario);

        Assertions.assertTrue(resultPatch.correct);
        Assertions.assertEquals(200, resultPatch.status);

        Usuario usuarioActualizado = (Usuario) resultPatch.Object;
        Assertions.assertEquals("Ulises", usuarioActualizado.getName());
        Assertions.assertEquals("prueba1@gmail.com", usuarioActualizado.getEmail());
        Assertions.assertEquals("5544332211", usuarioActualizado.getPhone().get(0));
    }

    @Test
    public void PatchEmailOk() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("prueba2@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5544332212"));

        Result resultAdd = logicService.Add(usuarioBase);

        Assertions.assertTrue(resultAdd.correct);
        Assertions.assertEquals(200, resultAdd.status);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;
        String idGuardado = usuarioGuardado.getId();

        Usuario usuario = new Usuario();
        usuario.setId(idGuardado);
        usuario.setEmail("ulises12@gmail.com");

        Result resultPatch = logicService.Patch(usuario);

        Assertions.assertTrue(resultPatch.correct);
        Assertions.assertEquals(200, resultPatch.status);

        Usuario usuarioActualizado = (Usuario) resultPatch.Object;
        Assertions.assertEquals("Prueba", usuarioActualizado.getName());
        Assertions.assertEquals("ulises12@gmail.com", usuarioActualizado.getEmail());
        Assertions.assertEquals("5544332212", usuarioActualizado.getPhone().get(0));
    }

    @Test
    public void PatchPhoneOk() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("prueba3@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5544332213"));

        Result resultAdd = logicService.Add(usuarioBase);

        Assertions.assertTrue(resultAdd.correct);
        Assertions.assertEquals(200, resultAdd.status);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;
        String idGuardado = usuarioGuardado.getId();

        Usuario usuario = new Usuario();
        usuario.setId(idGuardado);
        usuario.setPhone(Arrays.asList("5514006780"));

        Result resultPatch = logicService.Patch(usuario);

        Assertions.assertTrue(resultPatch.correct);
        Assertions.assertEquals(200, resultPatch.status);

        Usuario usuarioActualizado = (Usuario) resultPatch.Object;
        Assertions.assertEquals("Prueba", usuarioActualizado.getName());
        Assertions.assertEquals("prueba3@gmail.com", usuarioActualizado.getEmail());
        Assertions.assertEquals("5514006780", usuarioActualizado.getPhone().get(0));
    }

    @Test
    public void PatchFailNullId() {

        Usuario usuario = new Usuario();
        usuario.setEmail("ulises12@gmail.com");

        Result resultPatch = logicService.Patch(usuario);

        Assertions.assertFalse(resultPatch.correct);
        Assertions.assertEquals(400, resultPatch.status);
        Assertions.assertEquals("Usuario nulo o sin ID", resultPatch.errorMessage);
    }

    @Test
    public void PatchFailNoUser() {

        Usuario usuario = new Usuario();
        usuario.setId("id-inexistente");
        usuario.setName("Ulises");

        Result resultPatch = logicService.Patch(usuario);

        Assertions.assertFalse(resultPatch.correct);
        Assertions.assertEquals(404, resultPatch.status);
        Assertions.assertEquals("Usuario no encontrado", resultPatch.errorMessage);
    }

    @Test
    public void PatchFailNameNum() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("prueba4@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5544332214"));

        Result resultAdd = logicService.Add(usuarioBase);
        Assertions.assertTrue(resultAdd.correct);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioGuardado.getId());
        usuario.setName("Ulises123");

        Result resultPatch = logicService.Patch(usuario);

        Assertions.assertFalse(resultPatch.correct);
        Assertions.assertEquals(400, resultPatch.status);
        Assertions.assertEquals("Name solo debe contener letras", resultPatch.errorMessage);
    }

    @Test
    public void PatchFailEmailBad() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("prueba5@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5544332215"));

        Result resultAdd = logicService.Add(usuarioBase);
        Assertions.assertTrue(resultAdd.correct);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioGuardado.getId());
        usuario.setEmail("correo@");

        Result resultPatch = logicService.Patch(usuario);

        Assertions.assertFalse(resultPatch.correct);
        Assertions.assertEquals(400, resultPatch.status);
        Assertions.assertEquals("Email inválido", resultPatch.errorMessage);
    }

    @Test
    public void PatchFailPhoneBad() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("prueba6@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5544332216"));

        Result resultAdd = logicService.Add(usuarioBase);
        Assertions.assertTrue(resultAdd.correct);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioGuardado.getId());
        usuario.setPhone(Arrays.asList("12345abcd0"));

        Result resultPatch = logicService.Patch(usuario);

        Assertions.assertFalse(resultPatch.correct);
        Assertions.assertEquals(400, resultPatch.status);
        Assertions.assertEquals("Teléfono debe tener 10 dígitos numéricos", resultPatch.errorMessage);
    }

    @Test
    public void PatchFailEmailDup() {

        Usuario usuarioBase1 = new Usuario();
        usuarioBase1.setName("Carlos");
        usuarioBase1.setEmail("carlosdup@gmail.com");
        usuarioBase1.setPhone(Arrays.asList("5511111111"));

        Result resultAdd1 = logicService.Add(usuarioBase1);
        Assertions.assertTrue(resultAdd1.correct);

        Usuario usuarioBase2 = new Usuario();
        usuarioBase2.setName("Luis");
        usuarioBase2.setEmail("luisdup@gmail.com");
        usuarioBase2.setPhone(Arrays.asList("5522222222"));

        Result resultAdd2 = logicService.Add(usuarioBase2);
        Assertions.assertTrue(resultAdd2.correct);

        Usuario usuarioGuardado2 = (Usuario) resultAdd2.Object;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioGuardado2.getId());
        usuario.setEmail("carlosdup@gmail.com");

        Result resultPatch = logicService.Patch(usuario);

        Assertions.assertFalse(resultPatch.correct);
        Assertions.assertEquals(400, resultPatch.status);
        Assertions.assertEquals("Email ya existe", resultPatch.errorMessage);
    }

    @Test
    public void PatchFailPhoneDup() {

        Usuario usuarioBase1 = new Usuario();
        usuarioBase1.setName("Carlos");
        usuarioBase1.setEmail("carlosphone@gmail.com");
        usuarioBase1.setPhone(Arrays.asList("5533333333"));

        Result resultAdd1 = logicService.Add(usuarioBase1);
        Assertions.assertTrue(resultAdd1.correct);

        Usuario usuarioBase2 = new Usuario();
        usuarioBase2.setName("Luis");
        usuarioBase2.setEmail("luisphone@gmail.com");
        usuarioBase2.setPhone(Arrays.asList("5544444444"));

        Result resultAdd2 = logicService.Add(usuarioBase2);
        Assertions.assertTrue(resultAdd2.correct);

        Usuario usuarioGuardado2 = (Usuario) resultAdd2.Object;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioGuardado2.getId());
        usuario.setPhone(Arrays.asList("5533333333"));

        Result resultPatch = logicService.Patch(usuario);

        Assertions.assertFalse(resultPatch.correct);
        Assertions.assertEquals(400, resultPatch.status);
        Assertions.assertEquals("Teléfono ya existe", resultPatch.errorMessage);
    }
}
