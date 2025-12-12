package com.ApiService.ApiService;

import com.ApiService.ApiService.Entity.Result;
import com.ApiService.ApiService.Entity.Usuario;
import com.ApiService.ApiService.Service.LogicService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestGetAll {

    @Autowired
    private LogicService logicService;

    @Test
    public void GetAllOk() {

        Result result = logicService.GetAll("");

        Assertions.assertTrue(result.correct);
        Assertions.assertEquals(200, result.status);
        Assertions.assertNotNull(result.Object);

        List<Usuario> listaUsuarios = (List<Usuario>) result.Object;
        Assertions.assertFalse(listaUsuarios.isEmpty());
    }

    @Test
    public void GetAllId() {

        Result result = logicService.GetAll("id");

        Assertions.assertTrue(result.correct);
        Assertions.assertEquals(200, result.status);
        Assertions.assertNotNull(result.Object);

        List<Usuario> listaUsuarios = (List<Usuario>) result.Object;

        for (int i = 0; i < listaUsuarios.size() - 1; i++) {
            String idActual = listaUsuarios.get(i).getId();
            String idSiguiente = listaUsuarios.get(i + 1).getId();
            Assertions.assertTrue(idActual.compareTo(idSiguiente) <= 0);
        }
    }

    @Test
    public void GetAllName() {

        Result result = logicService.GetAll("name");

        Assertions.assertTrue(result.correct);
        Assertions.assertEquals(200, result.status);
        Assertions.assertNotNull(result.Object);

        List<Usuario> listaUsuarios = (List<Usuario>) result.Object;

        for (int i = 0; i < listaUsuarios.size() - 1; i++) {
            String nameActual = listaUsuarios.get(i).getName();
            String nameSiguiente = listaUsuarios.get(i + 1).getName();
            Assertions.assertTrue(nameActual.compareToIgnoreCase(nameSiguiente) <= 0);
        }
    }

    @Test
    public void GetAllEmail() {

        Result result = logicService.GetAll("email");

        Assertions.assertTrue(result.correct);
        Assertions.assertEquals(200, result.status);
        Assertions.assertNotNull(result.Object);

        List<Usuario> listaUsuarios = (List<Usuario>) result.Object;

        for (int i = 0; i < listaUsuarios.size() - 1; i++) {
            String emailActual = listaUsuarios.get(i).getEmail();
            String emailSiguiente = listaUsuarios.get(i + 1).getEmail();
            Assertions.assertTrue(emailActual.compareToIgnoreCase(emailSiguiente) <= 0);
        }
    }

    @Test
    public void GetAllPhone() {

        Result result = logicService.GetAll("phone");

        Assertions.assertTrue(result.correct);
        Assertions.assertEquals(200, result.status);
        Assertions.assertNotNull(result.Object);

        List<Usuario> listaUsuarios = (List<Usuario>) result.Object;

        for (int i = 0; i < listaUsuarios.size() - 1; i++) {
            String phoneActual = listaUsuarios.get(i).getPhone().get(0);
            String phoneSiguiente = listaUsuarios.get(i + 1).getPhone().get(0);
            Assertions.assertTrue(phoneActual.compareTo(phoneSiguiente) <= 0);
        }
    }
}
