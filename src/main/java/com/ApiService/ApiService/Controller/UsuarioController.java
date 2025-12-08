package com.ApiService.ApiService.Controller;

import com.ApiService.ApiService.Service.LogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    
    @Autowired
    private LogicService logicService;
    
    
}
