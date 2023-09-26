/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.services;

import com.sistema.avv.savv.models.Reservacion;
import com.sistema.avv.savv.models.Vehiculo;
import com.sistema.avv.savv.repository.ReservacionRepository;
import com.sistema.avv.savv.repository.VehiculoRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jdesquivia
 */
@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    private final ReservacionRepository reservacionRepository;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository, ReservacionRepository reservacionRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.reservacionRepository = reservacionRepository;

    }

    // Método para buscar vehículos por marca y modelo
    public List<Vehiculo> buscarVehiculosPorMarcaYModelo(String marca, String modelo) {
        return vehiculoRepository.findByMarcaAndModelo(marca, modelo);
    }

    public List<Vehiculo> listarVehiculo() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo obtenerVehiculoForID(Long vehiculoId) {
        return vehiculoRepository.findById(vehiculoId).orElseThrow(()
                -> new RuntimeException("Vehiculo no encontrado"));
    }

    public boolean verificarDisponibilidad(Long vehiculoId, Date fechaInicio, Date fechaFin) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElse(null);
        if (vehiculo == null) {
            return false;
        }

        List<Reservacion> reservaciones = reservacionRepository.findByVehiculoAndFechas(vehiculo, fechaInicio, fechaFin);

        if (reservaciones.isEmpty()) {
            return true;
        } else {

            for (Reservacion reservacion : reservaciones) {
                if (fechasSeSuperponen(reservacion.getFecha_inicio(), reservacion.getFecha_fin(), fechaInicio, fechaFin)) {
                    return false; // Las fechas se superponen con una reservación existente
                }
            }
            return true; // No se superponen con ninguna reservación existente
        }
    }

// Función para verificar si dos rangos de fechas se superponen
    private boolean fechasSeSuperponen(Date inicio1, Date fin1, Date inicio2, Date fin2) {
        return inicio1.before(fin2) && fin1.after(inicio2);
    }
}
