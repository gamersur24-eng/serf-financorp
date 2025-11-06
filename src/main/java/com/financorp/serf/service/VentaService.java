package com.financorp.serf.service;

import com.financorp.serf.model.Producto;
import com.financorp.serf.model.Venta;
import com.financorp.serf.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {
    
    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private ProductoService productoService;
    
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }
    
    public Venta getVentaById(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }
    
    public Venta getVentaByCodigoVenta(String codigoVenta) {
        return ventaRepository.findByCodigoVenta(codigoVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }
    
    public List<Venta> getVentasByFilial(Long filialId) {
        return ventaRepository.findByFilialId(filialId);
    }
    
    public List<Venta> getVentasByEstado(Venta.EstadoVenta estado) {
        return ventaRepository.findByEstado(estado);
    }
    
    public List<Venta> getVentasByPeriodo(LocalDateTime start, LocalDateTime end) {
        return ventaRepository.findByFechaVentaBetween(start, end);
    }
    
    @Transactional
    public Venta createVenta(Venta venta) {
        // Actualizar stock del producto
        Producto producto = venta.getProducto();
        int nuevoStock = producto.getStockActual() - venta.getCantidad();
        
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock insuficiente para completar la venta");
        }
        
        productoService.updateStock(producto.getId(), nuevoStock);
        
        // Guardar la venta
        return ventaRepository.save(venta);
    }
    
    public Venta updateVenta(Long id, Venta venta) {
        Venta existing = getVentaById(id);
        existing.setCantidad(venta.getCantidad());
        existing.setPrecioUnitario(venta.getPrecioUnitario());
        existing.setImpuesto(venta.getImpuesto());
        existing.setClienteNombre(venta.getClienteNombre());
        existing.setClienteDocumento(venta.getClienteDocumento());
        existing.setEstado(venta.getEstado());
        existing.setFormaPago(venta.getFormaPago());
        existing.setObservaciones(venta.getObservaciones());
        return ventaRepository.save(existing);
    }
    
    public void deleteVenta(Long id) {
        ventaRepository.deleteById(id);
    }
    
    public Double getTotalVentasByFilial(Long filialId) {
        return ventaRepository.getTotalVentasByFilial(filialId);
    }
}
