/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.controllers;

import com.sistema.avv.savv.models.Usuario;
import com.sistema.avv.savv.services.LoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jdesquivia
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginServices loginServices;

    @PostMapping
    public ResponseEntity<String> iniciarSesio(@RequestBody Usuario usuario) {
        boolean usuarioEncontrado = loginServices.iniciarSesion(usuario.getNombre(), usuario.getContrasenia());

        if (usuarioEncontrado) {
            return ResponseEntity.ok("Inicio de sesión exitoso");
        } else {
            return ResponseEntity.badRequest().body("Usuario o contraseña incorrectos");
        }
    }

}
