package com.financorp.serf.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa una Venta realizada por una filial
 */
@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String codigoVenta; // Ej: ES-001-V-00001
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filial_id", nullable = false)
    private Filial filial;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    
    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
    
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal impuesto = BigDecimal.ZERO; // % de impuesto
    
    @Column(name = "monto_impuesto", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoImpuesto = BigDecimal.ZERO;
    
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(name = "cliente_nombre")
    private String clienteNombre;
    
    @Column(name = "cliente_documento")
    private String clienteDocumento;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoVenta estado = EstadoVenta.COMPLETADA;
    
    @Column(name = "forma_pago")
    private String formaPago; // Efectivo, Tarjeta, Transferencia
    
    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;
    
    @Column(length = 500)
    private String observaciones;
    
    @PrePersist
    protected void onCreate() {
        if (fechaVenta == null) {
            fechaVenta = LocalDateTime.now();
        }
        calcularTotales();
    }
    
    @PreUpdate
    protected void onUpdate() {
        calcularTotales();
    }
    
    /**
     * Calcula los totales de la venta
     */
    private void calcularTotales() {
        if (cantidad != null && precioUnitario != null) {
            this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
            this.montoImpuesto = subtotal.multiply(impuesto).divide(BigDecimal.valueOf(100));
            this.total = subtotal.add(montoImpuesto);
        }
    }
    
    /**
     * Enum para estados de venta
     */
    public enum EstadoVenta {
        COMPLETADA("Completada"),
        PENDIENTE("Pendiente"),
        CANCELADA("Cancelada"),
        DEVUELTA("Devuelta");
        
        private final String descripcion;
        
        EstadoVenta(String descripcion) {
            this.descripcion = descripcion;
        }
        
        public String getDescripcion() {
            return descripcion;
        }
    }
}
