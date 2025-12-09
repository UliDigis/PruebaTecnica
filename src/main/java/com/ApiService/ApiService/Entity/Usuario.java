package com.ApiService.ApiService.Entity;

import java.util.List;
import java.util.UUID;


public class Usuario {

    private String id;
    
    private String name;
    
    private String email;
    
    private List<String> phone;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }
    
    
    
    
    
}
