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
public class TestUpdate {

    @Autowired
    private LogicService logicService;

    @Test
    public void UpdateOk() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("update1@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5512345678"));

        Result resultAdd = logicService.Add(usuarioBase);

        Assertions.assertTrue(resultAdd.correct);
        Assertions.assertEquals(200, resultAdd.status);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;
        String idGuardado = usuarioGuardado.getId();

        Usuario usuario = new Usuario();
        usuario.setId(idGuardado);
        usuario.setName("Ulises");
        usuario.setEmail("update2@gmail.com");
        usuario.setPhone(Arrays.asList("5598765432"));

        Result resultUpdate = logicService.Update(usuario);

        Assertions.assertTrue(resultUpdate.correct);
        Assertions.assertEquals(200, resultUpdate.status);

        Usuario usuarioActualizado = (Usuario) resultUpdate.Object;
        Assertions.assertEquals("Ulises", usuarioActualizado.getName());
        Assertions.assertEquals("update2@gmail.com", usuarioActualizado.getEmail());
        Assertions.assertEquals("5598765432", usuarioActualizado.getPhone().get(0));
    }

    @Test
    public void UpdateFailNullId() {

        Usuario usuario = new Usuario();
        usuario.setName("Ulises");
        usuario.setEmail("update3@gmail.com");
        usuario.setPhone(Arrays.asList("5512345679"));

        Result resultUpdate = logicService.Update(usuario);

        Assertions.assertFalse(resultUpdate.correct);
        Assertions.assertEquals(400, resultUpdate.status);
        Assertions.assertEquals("Usuario nulo o sin ID", resultUpdate.errorMessage);
    }

    @Test
    public void UpdateFailNoUser() {

        Usuario usuario = new Usuario();
        usuario.setId("id-inexistente");
        usuario.setName("Ulises");
        usuario.setEmail("update4@gmail.com");
        usuario.setPhone(Arrays.asList("5512345680"));

        Result resultUpdate = logicService.Update(usuario);

        Assertions.assertFalse(resultUpdate.correct);
        Assertions.assertEquals(404, resultUpdate.status);
        Assertions.assertEquals("Usuario no encontrado", resultUpdate.errorMessage);
    }

    @Test
    public void UpdateFailNameNum() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("update5@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5512345681"));

        Result resultAdd = logicService.Add(usuarioBase);
        Assertions.assertTrue(resultAdd.correct);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioGuardado.getId());
        usuario.setName("Ulises123");
        usuario.setEmail("update6@gmail.com");
        usuario.setPhone(Arrays.asList("5512345682"));

        Result resultUpdate = logicService.Update(usuario);

        Assertions.assertFalse(resultUpdate.correct);
        Assertions.assertEquals(400, resultUpdate.status);
        Assertions.assertEquals("Name solo debe contener letras", resultUpdate.errorMessage);
    }

    @Test
    public void UpdateFailEmailBad() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("update7@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5512345683"));

        Result resultAdd = logicService.Add(usuarioBase);
        Assertions.assertTrue(resultAdd.correct);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioGuardado.getId());
        usuario.setName("Ulises");
        usuario.setEmail("correo@");
        usuario.setPhone(Arrays.asList("5512345684"));

        Result resultUpdate = logicService.Update(usuario);

        Assertions.assertFalse(resultUpdate.correct);
        Assertions.assertEquals(400, resultUpdate.status);
        Assertions.assertEquals("Email inválido", resultUpdate.errorMessage);
    }

    @Test
    public void UpdateFailPhoneBad() {

        Usuario usuarioBase = new Usuario();
        usuarioBase.setName("Prueba");
        usuarioBase.setEmail("update8@gmail.com");
        usuarioBase.setPhone(Arrays.asList("5512345685"));

        Result resultAdd = logicService.Add(usuarioBase);
        Assertions.assertTrue(resultAdd.correct);

        Usuario usuarioGuardado = (Usuario) resultAdd.Object;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioGuardado.getId());
        usuario.setName("Ulises");
        usuario.setEmail("update9@gmail.com");
        usuario.setPhone(Arrays.asList("12345abcd0"));

        Result resultUpdate = logicService.Update(usuario);

        Assertions.assertFalse(resultUpdate.correct);
        Assertions.assertEquals(400, resultUpdate.status);
        Assertions.assertEquals("Teléfono debe tener 10 dígitos numéricos", resultUpdate.errorMessage);
    }

    @Test
    public void UpdateFailEmailDup() {

        Usuario usuarioBase1 = new Usuario();
        usuarioBase1.setName("Carlos");
        usuarioBase1.setEmail("dupupdate@gmail.com");
        usuarioBase1.setPhone(Arrays.asList("5511111111"));

        Result resultAdd1 = logicService.Add(usuarioBase1);
        Assertions.assertTrue(resultAdd1.correct);

        Usuario usuarioBase2 = new Usuario();
        usuarioBase2.setName("Luis");
        usuarioBase2.setEmail("otroupdate@gmail.com");
        usuarioBase2.setPhone(Arrays.asList("5522222222"));

        Result resultAdd2 = logicService.Add(usuarioBase2);
        Assertions.assertTrue(resultAdd2.correct);

        Usuario usuarioGuardado2 = (Usuario) resultAdd2.Object;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioGuardado2.getId());
        usuario.setName("Luis");
        usuario.setEmail("dupupdate@gmail.com");
        usuario.setPhone(Arrays.asList("5522222222"));

        Result resultUpdate = logicService.Update(usuario);

        Assertions.assertFalse(resultUpdate.correct);
        Assertions.assertEquals(400, resultUpdate.status);
        Assertions.assertEquals("Email ya existe", resultUpdate.errorMessage);
    }

    @Test
    public void UpdateFailPhoneDup() {

        Usuario usuarioBase1 = new Usuario();
        usuarioBase1.setName("Carlos");
        usuarioBase1.setEmail("phonea@gmail.com");
        usuarioBase1.setPhone(Arrays.asList("5533333333"));

        Result resultAdd1 = logicService.Add(usuarioBase1);
        Assertions.assertTrue(resultAdd1.correct);

        Usuario usuarioBase2 = new Usuario();
        usuarioBase2.setName("Luis");
        usuarioBase2.setEmail("phoneb@gmail.com");
        usuarioBase2.setPhone(Arrays.asList("5544444444"));

        Result resultAdd2 = logicService.Add(usuarioBase2);
        Assertions.assertTrue(resultAdd2.correct);

        Usuario usuarioGuardado2 = (Usuario) resultAdd2.Object;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioGuardado2.getId());
        usuario.setName("Luis");
        usuario.setEmail("phoneb@gmail.com");
        usuario.setPhone(Arrays.asList("5533333333"));

        Result resultUpdate = logicService.Update(usuario);

        Assertions.assertFalse(resultUpdate.correct);
        Assertions.assertEquals(400, resultUpdate.status);
        Assertions.assertEquals("Teléfono ya existe", resultUpdate.errorMessage);
    }
}
