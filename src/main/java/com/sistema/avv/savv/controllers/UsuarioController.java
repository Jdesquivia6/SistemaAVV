/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.controllers;

import com.sistema.avv.savv.models.Usuario;
import com.sistema.avv.savv.services.UsuarioService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jdesquivia
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserId(@PathVariable Long id) {

        Usuario usuario = usuarioService.obtenerUsuarioForID(id);
        return ResponseEntity.ok(usuario);

    }

    @GetMapping("/listAllUsuario")
    public ResponseEntity<List<Usuario>> listAllUser() {
        try {
            return ResponseEntity.ok(usuarioService.listarUsuarios());
        } catch (Exception e) {
            e.getMessage();
            e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioNuevo = usuarioService.guardarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
        } catch (Exception e) {

            e.printStackTrace();

            String mensajeError = "Error al guardar el usuario: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {

        Map<String, Object> eliminado = usuarioService.eliminarUsuario(id);

        boolean exito = (boolean) eliminado.get("exito");

        if (exito) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarVehiculo(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
            if (usuarioActualizado != null) {
                return ResponseEntity.ok(usuarioActualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();

            String mensajeError = "Error al actualizar el vehiculo: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);

        }

    }

}
