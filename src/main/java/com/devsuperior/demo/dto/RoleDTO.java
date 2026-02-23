package com.devsuperior.demo.dto;

import com.devsuperior.demo.entities.Role;

import java.io.Serializable;

public class RoleDTO implements Serializable {

    private Long id;
    private String authority;

    public RoleDTO(Long id, String authrity) {
        this.id = id;
        this.authority = authrity;
    }
    public RoleDTO(){}

    public RoleDTO(Role entity){
        id = entity.getId();
        authority = entity.getAuthority();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthrity() {
        return authority;
    }

    public void setAuthrity(String authrity) {
        this.authority = authrity;
    }
}
