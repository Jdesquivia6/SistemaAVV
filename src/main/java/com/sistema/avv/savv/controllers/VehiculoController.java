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
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/save")
    public ResponseEntity<Vehiculo> saveVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo vehiculoNuevo = vehiculoService.guardarVehiculo(vehiculo);
            return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoNuevo);
        } catch (Exception e) {
            e.getMessage();
            e.getLocalizedMessage();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo vehiculoActualizado = vehiculoService.actualizarVehiculo(id, vehiculo);
            if (vehiculoActualizado != null) {
                return ResponseEntity.ok(vehiculoActualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();

            String mensajeError = "Error al actualizar el vehiculo: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);

        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {

        Map<String, Object> eliminado = vehiculoService.eliminarVehiculo(id);

        boolean exito = (boolean) eliminado.get("exito");

        if (exito) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
