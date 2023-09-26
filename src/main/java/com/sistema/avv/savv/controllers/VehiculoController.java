/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.controllers;

import com.sistema.avv.savv.models.Vehiculo;
import com.sistema.avv.savv.repository.VehiculoRepository;
import com.sistema.avv.savv.services.VehiculoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jdesquivia
 */
@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @Autowired
    private VehiculoRepository v;

    @Autowired
    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // Endpoint para buscar vehículos por marca y modelo
    @GetMapping("/buscar")
    public List<Vehiculo> buscarVehiculos(@RequestParam("marca") String marca, @RequestParam("modelo") String modelo) {
        return vehiculoService.buscarVehiculosPorMarcaYModelo(marca, modelo);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Vehiculo>> getAllVehiculo() {
        return ResponseEntity.ok(vehiculoService.listarVehiculo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> getByIdVehiculo(@PathVariable Long id) {
        Vehiculo vehiculo = vehiculoService.obtenerVehiculoForID(id);
        if (vehiculo != null) {
            return ResponseEntity.ok(vehiculo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    

}
