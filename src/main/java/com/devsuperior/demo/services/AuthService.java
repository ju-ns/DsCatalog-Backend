package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.EmailDTO;
import com.devsuperior.demo.dto.NewPasswordDTO;
import com.devsuperior.demo.entities.PasswordRecover;
import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.repositories.PasswordRecoverRepository;
import com.devsuperior.demo.repositories.UserRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createRecoverToken(EmailDTO body) {
        User user = userRepository.findByEmail(body.getEmail());
        if(user == null){
            throw new ResourceNotFoundException("E-mail not found");
        }

        String token = UUID.randomUUID().toString();

        PasswordRecover entity = new PasswordRecover();
        entity.setEmail(body.getEmail());
        entity.setToken(token);
        entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
        entity = passwordRecoverRepository.save(entity);

        String text = "Access the link to recover your password\n\n" +
                recoverUri + token ;
        emailService.sendEmail(body.getEmail(), "Password recover", text);
    }

    public void saveNewPassword(NewPasswordDTO dto) {
        List<PasswordRecover> notExpirate = passwordRecoverRepository.searchValidToken(dto.getToken(), Instant.now());
        if(notExpirate.isEmpty()){
            throw new ResourceNotFoundException("Token not found");
        }
        User user = userRepository.findByEmail(notExpirate.getFirst().getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user = userRepository.save(user);
    }

    protected User authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return userRepository.findByEmail(username);
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }



}
