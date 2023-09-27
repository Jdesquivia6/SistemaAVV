/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.controllers;

import com.sistema.avv.savv.models.Alquiler;
import com.sistema.avv.savv.repository.VehiculoRepository;
import com.sistema.avv.savv.services.AlquilerService;
import com.sistema.avv.savv.services.VehiculoService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jdesquivia
 */
@RestController
@RequestMapping("/api/reservacion")
public class ReservacionController {

    private final VehiculoService vehiculoService;
    private final AlquilerService alquilerService;

    @Autowired
    private VehiculoRepository v;

    @Autowired
    public ReservacionController(VehiculoService vehiculoService, AlquilerService alquilerService) {
        this.vehiculoService = vehiculoService;
        this.alquilerService = alquilerService;
    }

    @GetMapping("/listarAlquileres")
    public ResponseEntity<List<Alquiler>> getAllAlquiler() {
        return ResponseEntity.ok(alquilerService.getAllAlquiler());
    }

    @GetMapping("/verificar")
    public ResponseEntity<Boolean> verificarDisponibilidad(
            @RequestParam("vehiculoId") Long vehiculoId,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin
    ) {
        boolean disponibilidad = vehiculoService.verificarDisponibilidad(vehiculoId, fechaInicio, fechaFin);
        return ResponseEntity.ok(disponibilidad);
    }
}
