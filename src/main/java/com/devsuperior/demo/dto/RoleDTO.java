package com.devsuperior.demo.dto;

import com.devsuperior.demo.entities.Role;

import java.io.Serializable;

public class RoleDTO implements Serializable {

    private Long id;
    private String authrity;

    public RoleDTO(Long id, String authrity) {
        this.id = id;
        this.authrity = authrity;
    }
    public RoleDTO(){}

    public RoleDTO(Role entity){
        id = entity.getId();
        authrity = entity.getAuthority();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthrity() {
        return authrity;
    }

    public void setAuthrity(String authrity) {
        this.authrity = authrity;
    }
}
