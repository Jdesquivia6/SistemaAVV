/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.services;

import com.sistema.avv.savv.models.Alquiler;
import com.sistema.avv.savv.models.TiempoAlquiler;
import com.sistema.avv.savv.models.Usuario;
import com.sistema.avv.savv.models.Vehiculo;
import com.sistema.avv.savv.repository.AlquilerRespository;
import com.sistema.avv.savv.repository.ReservacionRepository;
import com.sistema.avv.savv.repository.TiempoAlquilerRespository;
import com.sistema.avv.savv.repository.VehiculoRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jdesquivia
 */
@Service
public class AlquilerService {

    private final VehiculoService vehiculoService;
    private final UsuarioService usuarioService;
    private final AlquilerRespository alquilerRepository;
    private final TiempoAlquilerRespository tiempoAlquilerRepository;
    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public AlquilerService(VehiculoRepository vehiculoRepository, VehiculoService vehiculoService, UsuarioService usuarioService, AlquilerRespository alquilerRepository,
            TiempoAlquilerRespository tiempoAlquilerRepository) {
        this.vehiculoService = vehiculoService;
        this.usuarioService = usuarioService;
        this.alquilerRepository = alquilerRepository;
        this.tiempoAlquilerRepository = tiempoAlquilerRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Alquiler> getAllAlquiler() {
        return alquilerRepository.findAll();
    }

    public Alquiler realizarAlquiler(Long usuarioId, Long vehiculoId, Date fechaEntrada, Date fechaSalida) {
        // Obtener el vehículo y verificar su disponibilidad
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("El vehículo no existe"));

        if (!vehiculoService.verificarDisponibilidad(vehiculoId, fechaEntrada, fechaSalida)) {
            throw new RuntimeException("El vehículo no está disponible en las fechas especificadas");
        }

        int duracionHoras = calcularDuracionHoras(fechaEntrada, fechaSalida);
        double costoTotal = calcularCostoTotal(vehiculo, duracionHoras);

        Usuario usuario = usuarioService.obtenerUsuarioForID(usuarioId);

        Alquiler alquiler = new Alquiler();
        alquiler.setUsuario(usuario);
        alquiler.setVehiculo(vehiculo);
        alquiler.setFechaEntrada(fechaEntrada);
        alquiler.setFechaSalida(fechaSalida);
        alquiler.setDuracionHoras(duracionHoras);
        alquiler.setCostoTotal(costoTotal);

        alquiler = alquilerRepository.save(alquiler);

        // Crear un registro en la clase TiempoAlquiler
        TiempoAlquiler tiempoAlquiler = new TiempoAlquiler();
        tiempoAlquiler.setAlquiler(alquiler);
        tiempoAlquiler.setVehiculo(vehiculo);
        tiempoAlquiler.setDuracionHoras(duracionHoras);
        tiempoAlquiler.setFechaInicioAlquiler(fechaEntrada);
        tiempoAlquiler.setFechaFinAlquiler(fechaSalida);
        tiempoAlquiler.setReservacion(null);

        tiempoAlquilerRepository.save(tiempoAlquiler);

        return alquiler;
    }

    private int calcularDuracionHoras(Date fechaEntrada, Date fechaSalida) {
        LocalDateTime inicio = fechaEntrada.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime fin = fechaSalida.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(inicio, fin);
        long duracionHoras = duration.toHours();

        if (duracionHoras <= 0) {
            throw new IllegalArgumentException("La duración del alquiler debe ser de al menos 1 hora.");
        }

        return (int) duracionHoras;
    }

    private double calcularCostoTotal(Vehiculo vehiculo, int duracionHoras) {
        double tarifaBase = vehiculo.getPrecioHora();
        double costoAdicional = 0.0;

        if (duracionHoras < 1) {
            throw new IllegalArgumentException("La duración del alquiler debe ser de al menos 1 hora.");
        }

        if (vehiculo.getTipo().equals("Lujo")) {
            costoAdicional += tarifaBase * 0.2;
        }

        boolean incluirSeguro = true; // Cambiar a false si el seguro no se incluye
        if (incluirSeguro) {
            costoAdicional += tarifaBase * 0.1;
        }

        int kilometrajeMaximo = 200; // Cambiar según el límite
        int kilometrajeRecorrido = 100; // Cambiar según la distancia recorrida
        if (kilometrajeRecorrido > kilometrajeMaximo) {
            costoAdicional += (kilometrajeRecorrido - kilometrajeMaximo) * 0.05;
        }

        double montoTotal = (tarifaBase + costoAdicional) * duracionHoras;
        return montoTotal;
    }

    public Alquiler obtenerAlquilerPorUsuarioVehiculo(Long usuarioId, Long vehiculoId) {
        return alquilerRepository.findByUsuarioIdAndVehiculoId(usuarioId, vehiculoId);
    }

    public Alquiler actualizarAlquiler(Alquiler alquiler) {
        if (alquiler == null) {
            throw new IllegalArgumentException("El alquiler no puede ser nulo.");
        }

        if (alquiler.getPagado() != null && alquiler.getPagado().equals("")) {
            throw new IllegalStateException("El alquiler ya ha sido pagado y no se puede actualizar.");
        }

        alquiler.setPagado(true);
        return alquilerRepository.save(alquiler);
    }
}
