/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.services;

import com.sistema.avv.savv.models.Alquiler;
import com.sistema.avv.savv.models.PagoAlquiler;
import com.sistema.avv.savv.repository.PagosRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jdesquivia
 */
@Service
public class PagosService {

    private final AlquilerService alquilerService;
    private final PagosRepository pagosRepository;

    @Autowired
    public PagosService(AlquilerService alquilerService, PagosRepository pagosRepository) {
        this.alquilerService = alquilerService;
        this.pagosRepository = pagosRepository;
    }

    public void realizarPagoAlquiler(Long usuarioId, Long vehiculoId, Double monto) {
        Alquiler alquiler = alquilerService.obtenerAlquilerPorUsuarioVehiculo(usuarioId, vehiculoId);

        if (alquiler == null) {
            throw new RuntimeException("No se encontró el alquiler.");
        }

        PagoAlquiler pagoAlquiler = new PagoAlquiler();
        pagoAlquiler.setAlquiler(alquiler);
        pagoAlquiler.setMontoPagado(alquiler.getCostoTotal());
        pagoAlquiler.setFechaPago(new Date());
        pagosRepository.save(pagoAlquiler);

        alquiler.setPagado(true);
        alquilerService.actualizarAlquiler(alquiler);
    }

    public List<PagoAlquiler> listAllPayments() {
        return pagosRepository.findAll();
    }

}
