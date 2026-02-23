package com.devsuperior.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {


    @NotBlank(message = "Mandatory field")
    @Email(message = "Invalid e-mail")
    private String email;

    public EmailDTO(){}

    public EmailDTO(String email) {
        this.email = email;
    }

    public @NotBlank(message = "Mandatory field") @Email(message = "Invalid e-mail") String getEmail() {
        return email;
    }
}
