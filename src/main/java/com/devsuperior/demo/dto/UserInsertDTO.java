package com.devsuperior.demo.dto;

import com.devsuperior.demo.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UserInsertValid
public class UserInsertDTO extends UserDTO{

    @NotBlank(message = "Mandatory field")
    @Size(min = 8, message = "Minimum 8 characters")
    private String password;

    public UserInsertDTO(){
        super();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
