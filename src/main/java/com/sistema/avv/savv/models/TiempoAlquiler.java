/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.avv.savv.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author jdesquivia
 */
@Entity
@Data
@Table(name = "tiempoalquiler")
public class TiempoAlquiler implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "reservacionid", referencedColumnName = "id")
    private Reservacion reservacion;

    @OneToOne
    @JoinColumn(name = "vehiculoid", referencedColumnName = "id")
    private Vehiculo vehiculo;
    
    @OneToOne
    @JoinColumn(name = "alquiler_id", referencedColumnName = "id")
    private Alquiler alquiler;

    @Column(name = "duracionhoras")
    private int duracionHoras;
    
    @Column(name = "fechainicioalquiler")
    private Date fechaInicioAlquiler;
    
    @Column(name = "fechafinalquiler")
    private Date fechaFinAlquiler;
    

}
