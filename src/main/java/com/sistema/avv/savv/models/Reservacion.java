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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author jdesquivia
 */
@Entity
@Data
@Table(name = "reservaciones")
public class Reservacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "usuarioid", referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "vehiculoid", referencedColumnName = "id")
    private Vehiculo vehiculo;

    @Column(name = "fechainicio")
    private Date fecha_inicio;
    
    @Column(name = "fechafin")
    private Date fecha_fin;
    
    @Column(name = "alquileriniciado")
    private boolean alquiler_iniciado;
}
