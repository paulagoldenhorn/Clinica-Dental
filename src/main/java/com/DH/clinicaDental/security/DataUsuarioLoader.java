package com.DH.clinicaDental.security;

import com.DH.clinicaDental.model.entity.Usuario;
import com.DH.clinicaDental.model.entity.UsuarioRol;
import com.DH.clinicaDental.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataUsuarioLoader implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder bcEncoder = new BCryptPasswordEncoder();

        String passwordUSER = "user";
        String passwordEncodedUSER = bcEncoder.encode(passwordUSER);
        Usuario usuarioUSER = new Usuario("basicUser", "user@gmail.com", passwordEncodedUSER, UsuarioRol.ROLE_USER);

        String passwordADMIN = "admin";
        String passwordEncodedADMIN = bcEncoder.encode(passwordADMIN);
        Usuario usuarioADMIN = new Usuario("superUser", "admin@gmail.com", passwordEncodedADMIN, UsuarioRol.ROLE_ADMIN);

        usuarioRepository.save(usuarioUSER);
        usuarioRepository.save(usuarioADMIN);
    }

}
