package com.ApiService.ApiService.Component;

import com.ApiService.ApiService.Entity.Usuario;
import com.ApiService.ApiService.Service.LogicService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StarUsuariosComponent {
    
    @Autowired
    private LogicService logicService;
    
    @PostConstruct
    public void Start(){
        //Usuario 1
        Usuario usuario = new Usuario();
        UUID generador1 = UUID.randomUUID();
        usuario.setId(generador1.toString());
        usuario.setName("Ulises");
        usuario.setEmail("ulises@gmail.com");
        
        List<String> phone = new ArrayList<>();
        phone.add("5566442243");
        phone.add("5647382903");
        usuario.setPhone(phone);
        
        //Usuario 2
        Usuario usuario2 = new Usuario();
        UUID generador2 = UUID.randomUUID();
        usuario2.setId(generador2.toString());
        usuario2.setName("Mariela");
        usuario2.setEmail("mariela@gmail.com");
        
        List<String> phone2 = new ArrayList<>();
        phone2.add("5648374902");
        phone2.add("5251234567");
        usuario2.setPhone(phone2);
        
        //Usuario 3
        Usuario usuario3 = new Usuario();
        UUID generador3 = UUID.randomUUID();
        usuario3.setId(generador3.toString());
        usuario3.setName("Adrian");
        usuario3.setEmail("adrian@gmail.com");
        
        List<String> phone3 = new ArrayList<>();
        phone3.add("5763425689");
        phone3.add("5113872012");
        usuario3.setPhone(phone3);
        
        logicService.Add(usuario);
        logicService.Add(usuario2);
        logicService.Add(usuario3);

        
    }
    
}
