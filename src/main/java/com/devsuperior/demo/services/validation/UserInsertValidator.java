package com.devsuperior.demo.services.validation;

import java.util.ArrayList;
import java.util.List;

import com.devsuperior.demo.dto.UserInsertDTO;
import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.repositories.UserRepository;
import com.devsuperior.demo.resources.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();
        User user = userRepository.findUserByEmail(dto.getEmail());
        if(user != null){
            list.add(new FieldMessage("email", "email already exist"));
        }
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}