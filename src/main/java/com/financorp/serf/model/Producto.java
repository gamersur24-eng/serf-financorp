package com.financorp.serf.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa un Producto Tecnológico importado de China
 */
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String sku; // Código único del producto
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(length = 1000)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProducto categoria;
    
    @Column(nullable = false)
    private String marca; // Ej: Apple, Samsung, Huawei, Lenovo
    
    @Column(nullable = false)
    private String modelo;
    
    @Column(name = "precio_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompra; // Precio de importación
    
    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;
    
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual = 0;
    
    @Column(name = "stock_minimo")
    private Integer stockMinimo = 10;
    
    @Column(name = "pais_origen")
    private String paisOrigen = "China";
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filial_id", nullable = false)
    private Filial filial;
    
    @Column(name = "fecha_importacion")
    private LocalDateTime fechaImportacion;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (fechaImportacion == null) {
            fechaImportacion = LocalDateTime.now();
        }
    }
    
    /**
     * Enum para categorías de productos tecnológicos
     */
    public enum CategoriaProducto {
        LAPTOP("Laptops"),
        SMARTPHONE("Smartphones"),
        TABLET("Tablets"),
        ACCESORIO("Accesorios Electrónicos"),
        EQUIPO_RED("Equipos de Red"),
        MONITOR("Monitores"),
        PERIFERICO("Periféricos"),
        COMPONENTE("Componentes");
        
        private final String descripcion;
        
        CategoriaProducto(String descripcion) {
            this.descripcion = descripcion;
        }
        
        public String getDescripcion() {
            return descripcion;
        }
    }
}
