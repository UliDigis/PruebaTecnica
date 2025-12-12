package com.ApiService.ApiService;

import com.ApiService.ApiService.Entity.Result;
import com.ApiService.ApiService.Entity.Usuario;
import com.ApiService.ApiService.Service.LogicService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiServiceApplicationTests {

    @Autowired
    private LogicService logicService;

    //Deberia agregar un usuario cuando los datos son validos
    @Test
    public void UsuarioValid(){
        
        Usuario usuario = new Usuario();
        usuario.setName("Kevin");
        usuario.setEmail("kevin@gmail.com");
        usuario.setPhone(Arrays.asList("5584756248","5514006780"));
        
        Result result = logicService.Add(usuario);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertEquals(200,result.status);
        Assertions.assertNotNull(result.Object);
    }
    
    //Deberia de si email esta vacio
    @Test
    public void UsuarioEmailNull(){
        
        Usuario usuario = new Usuario();
        usuario.setName("Kevin");
        usuario.setEmail("");
        usuario.setPhone(Arrays.asList("5584756248","5514006780"));
        
        Result result = logicService.Add(usuario);
        
        Assertions.assertFalse(result.correct);
        Assertions.assertEquals(400,result.status);
        Assertions.assertEquals("Email vacío", result.errorMessage);
    }
    
    //Deberia de si Name esta vacio
    @Test
    public void UsuarioNameNull(){
        
        Usuario usuario = new Usuario();
        usuario.setName("");
        usuario.setEmail("kevin@gmail.com");
        usuario.setPhone(Arrays.asList("5584756248","5514006780"));
        
        Result result = logicService.Add(usuario);
        
        Assertions.assertFalse(result.correct);
        Assertions.assertEquals(400,result.status);
        Assertions.assertEquals("Name vacío", result.errorMessage);
    }
    
    //Test para agregar un usuario y comprobar si se agrego correctamente
    @Test
    public void GetAllTrue() {
        
        Result result = logicService.GetAll("");
        
        Assertions.assertTrue(result.correct);
        Assertions.assertEquals(200, result.status);
        
    }

}
