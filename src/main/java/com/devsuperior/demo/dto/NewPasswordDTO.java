package com.devsuperior.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO {

    @NotBlank(message = "Mandatory field")
    private String token;

    @Size(min = 8, message = "Minimum 8 characters")
    private String password;

    public NewPasswordDTO(){}

    public NewPasswordDTO(String token, String password) {
        this.token = token;
        this.password = password;
    }

    public @NotBlank(message = "Mandatory field") String getToken() {
        return token;
    }

    public void setToken(@NotBlank(message = "Mandatory field") String token) {
        this.token = token;
    }

    public @Size(min = 8, message = "Minimum 8 characters") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 8, message = "Minimum 8 characters") String password) {
        this.password = password;
    }
}
