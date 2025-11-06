package com.financorp.serf.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una Filial de FinanCorp S.A.
 */
@Entity
@Table(name = "filiales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String codigo; // Ej: ES-001, MX-001, AR-001
    
    @Column(nullable = false)
    private String nombre; // Nombre de la filial
    
    @Column(nullable = false)
    private String pais; // País de la filial
    
    @Column(nullable = false)
    private String ciudad; // Ciudad
    
    @Column(nullable = false)
    private String moneda; // EUR, MXN, ARS, PEN, etc.
    
    @Column(nullable = false)
    private String responsable; // Gerente de la filial
    
    @Column(nullable = false)
    private Boolean activa = true;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL)
    private List<Producto> productos = new ArrayList<>();
    
    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL)
    private List<Venta> ventas = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}
