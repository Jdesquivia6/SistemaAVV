/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.controllers;

import com.sistema.avv.savv.models.PagoAlquiler;
import com.sistema.avv.savv.services.PagosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jdesquivia
 */
@RestController
@RequestMapping("/api/pagos")
public class PagosController {

    private final PagosService pagosService;

    @Autowired
    public PagosController(PagosService pagosService) {
        this.pagosService = pagosService;
    }

    @GetMapping("/listarPagos")
    public ResponseEntity<List<PagoAlquiler>> listPayments() {
        return ResponseEntity.ok(pagosService.listAllPayments());
    }

    @PostMapping("/realizarPagoAlquiler")
    public ResponseEntity<String> realizarPagoAlquiler(@RequestParam Long usuarioId, @RequestParam Long vehiculoId, @RequestParam Double monto) {

        try {
            pagosService.realizarPagoAlquiler(usuarioId, vehiculoId, monto);
            return ResponseEntity.ok("Pago de alquiler realizado con éxito");
        } catch (Exception e) {
            String mensajeError = "Error al realizar el pago de alquiler: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }

}
