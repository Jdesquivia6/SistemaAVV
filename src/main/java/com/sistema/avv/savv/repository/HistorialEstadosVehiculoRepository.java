/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.repository;

import com.sistema.avv.savv.models.HistorialEstadosVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jdesquivia
 */
@Repository
public interface HistorialEstadosVehiculoRepository extends JpaRepository<HistorialEstadosVehiculo, Long> {

}
