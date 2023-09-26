/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.controllers;

import com.sistema.avv.savv.services.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/realizarPagoAlquiler")
    public void realizarPagoAlquiler(@RequestParam Long usuarioId, @RequestParam Long vehiculoId, @RequestParam Double monto) {

        pagosService.realizarPagoAlquiler(usuarioId, vehiculoId, monto);

    }

}
