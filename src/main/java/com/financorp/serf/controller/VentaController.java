package com.financorp.serf.controller;

import com.financorp.serf.model.Venta;
import com.financorp.serf.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {
    
    @Autowired
    private VentaService ventaService;
    
    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        return ResponseEntity.ok(ventaService.getAllVentas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.getVentaById(id));
    }
    
    @GetMapping("/codigo/{codigoVenta}")
    public ResponseEntity<Venta> getVentaByCodigoVenta(@PathVariable String codigoVenta) {
        return ResponseEntity.ok(ventaService.getVentaByCodigoVenta(codigoVenta));
    }
    
    @GetMapping("/filial/{filialId}")
    public ResponseEntity<List<Venta>> getVentasByFilial(@PathVariable Long filialId) {
        return ResponseEntity.ok(ventaService.getVentasByFilial(filialId));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Venta>> getVentasByEstado(@PathVariable Venta.EstadoVenta estado) {
        return ResponseEntity.ok(ventaService.getVentasByEstado(estado));
    }
    
    @GetMapping("/periodo")
    public ResponseEntity<List<Venta>> getVentasByPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        return ResponseEntity.ok(ventaService.getVentasByPeriodo(fechaInicio, fechaFin));
    }
    
    @GetMapping("/filial/{filialId}/total")
    public ResponseEntity<Map<String, Object>> getTotalVentasByFilial(@PathVariable Long filialId) {
        Double total = ventaService.getTotalVentasByFilial(filialId);
        Map<String, Object> response = new HashMap<>();
        response.put("filialId", filialId);
        response.put("totalVentas", total != null ? total : 0);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<Venta> createVenta(@RequestBody Venta venta) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.createVenta(venta));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Long id, 
                                            @RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.updateVenta(id, venta));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        ventaService.deleteVenta(id);
        return ResponseEntity.noContent().build();
    }
}
