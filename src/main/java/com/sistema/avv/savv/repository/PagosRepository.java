/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.repository;

import com.sistema.avv.savv.models.PagoAlquiler;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jdesquivia
 */
public interface PagosRepository extends JpaRepository<PagoAlquiler, Long>{
    
}
