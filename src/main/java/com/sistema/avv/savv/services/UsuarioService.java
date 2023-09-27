/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.services;

import com.sistema.avv.savv.models.Rol;
import com.sistema.avv.savv.models.Usuario;
import com.sistema.avv.savv.repository.RolRepository;
import com.sistema.avv.savv.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jdesquivia
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public Usuario obtenerUsuarioForID(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(()
                -> new RuntimeException("Usuario no encontrado"));
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findByNombre(usuario.getNombre());
        if (usuarioExistente != null) {
            throw new RuntimeException("El usuario ya existe.");
        }

        String nombreRol = usuario.getRol().getRol();

        if (nombreRol != null && !nombreRol.isEmpty()) {

            Rol rolExistente = rolRepository.findByRol(nombreRol);

            if (rolExistente != null) {
                usuario.setRol(rolExistente);
            } else {
                throw new RuntimeException("El rol especificado no existe.");
            }
        } else {
            throw new RuntimeException("Debe especificar un nombre de rol.");
        }
        
        return usuarioRepository.save(usuario);
    }
}
