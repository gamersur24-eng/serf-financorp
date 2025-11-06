package com.financorp.serf.service;

import com.financorp.serf.model.Producto;
import com.financorp.serf.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }
    
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
    
    public Producto getProductoBySku(String sku) {
        return productoRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
    
    public List<Producto> getProductosByFilial(Long filialId) {
        return productoRepository.findByFilialId(filialId);
    }
    
    public List<Producto> getProductosByCategoria(Producto.CategoriaProducto categoria) {
        return productoRepository.findByCategoria(categoria);
    }
    
    public List<Producto> getProductosConBajoStock() {
        return productoRepository.findByStockActualLessThanStockMinimo();
    }
    
    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    
    public Producto updateProducto(Long id, Producto producto) {
        Producto existing = getProductoById(id);
        existing.setNombre(producto.getNombre());
        existing.setDescripcion(producto.getDescripcion());
        existing.setCategoria(producto.getCategoria());
        existing.setMarca(producto.getMarca());
        existing.setModelo(producto.getModelo());
        existing.setPrecioCompra(producto.getPrecioCompra());
        existing.setPrecioVenta(producto.getPrecioVenta());
        existing.setStockActual(producto.getStockActual());
        existing.setStockMinimo(producto.getStockMinimo());
        return productoRepository.save(existing);
    }
    
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
    
    public Producto updateStock(Long id, Integer cantidad) {
        Producto producto = getProductoById(id);
        producto.setStockActual(cantidad);
        return productoRepository.save(producto);
    }
}
