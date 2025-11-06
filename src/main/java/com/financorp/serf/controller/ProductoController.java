package com.financorp.serf.controller;

import com.financorp.serf.model.Producto;
import com.financorp.serf.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }
    
    @GetMapping("/sku/{sku}")
    public ResponseEntity<Producto> getProductoBySku(@PathVariable String sku) {
        return ResponseEntity.ok(productoService.getProductoBySku(sku));
    }
    
    @GetMapping("/filial/{filialId}")
    public ResponseEntity<List<Producto>> getProductosByFilial(@PathVariable Long filialId) {
        return ResponseEntity.ok(productoService.getProductosByFilial(filialId));
    }
    
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> getProductosByCategoria(
            @PathVariable Producto.CategoriaProducto categoria) {
        return ResponseEntity.ok(productoService.getProductosByCategoria(categoria));
    }
    
    @GetMapping("/bajo-stock")
    public ResponseEntity<List<Producto>> getProductosConBajoStock() {
        return ResponseEntity.ok(productoService.getProductosConBajoStock());
    }
    
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.createProducto(producto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, 
                                                   @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.updateProducto(id, producto));
    }
    
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Producto> updateStock(@PathVariable Long id, 
                                               @RequestParam Integer cantidad) {
        return ResponseEntity.ok(productoService.updateStock(id, cantidad));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
