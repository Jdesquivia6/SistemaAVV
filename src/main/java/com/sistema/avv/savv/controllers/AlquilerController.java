/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.controllers;

import com.sistema.avv.savv.models.Alquiler;
import com.sistema.avv.savv.services.AlquilerService;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jdesquivia
 */
@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService alquilerService;

    @Autowired
    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    @PostMapping("/realizarAlquiler")
    public ResponseEntity<Alquiler> realizarAlquiler(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("vehiculoId") Long vehiculoId,
            @RequestParam("fechaEntrada") String fechaEntradaStr,
            @RequestParam("fechaSalida") String fechaSalidaStr
    ) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date fechaEntrada = dateFormat.parse(fechaEntradaStr);
            Date fechaSalida = dateFormat.parse(fechaSalidaStr);

            Alquiler alquiler = alquilerService.realizarAlquiler(usuarioId, vehiculoId, fechaEntrada, fechaSalida);
            return ResponseEntity.ok(alquiler);
        } catch (Exception e) {
            e.getMessage();
            e.getLocalizedMessage();

            return ResponseEntity.badRequest().build();
        }
    }

}
