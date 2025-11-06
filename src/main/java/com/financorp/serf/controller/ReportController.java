package com.financorp.serf.controller;

import com.financorp.serf.patterns.creational.ReportFactory;
import com.financorp.serf.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la generación de reportes
 */
@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    /**
     * Genera un reporte usando el Factory Pattern
     */
    @PostMapping("/generar")
    public ResponseEntity<Map<String, String>> generateReport(
            @RequestParam ReportFactory.ReportType tipo,
            @RequestParam String pais,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(defaultValue = "Sistema SERF") String generadoPor) {
        
        String reporte = reportService.generateReport(tipo, pais, fechaInicio, fechaFin, generadoPor);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("tipo", tipo.getDescription());
        response.put("reporte", reporte);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Genera un reporte personalizado usando el Builder Pattern
     */
    @PostMapping("/personalizado")
    public ResponseEntity<Map<String, String>> generateCustomReport(
            @RequestParam String titulo,
            @RequestParam String tipoReporte,
            @RequestParam String pais,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) List<String> filiales,
            @RequestParam(defaultValue = "false") boolean incluirGraficos,
            @RequestParam(defaultValue = "false") boolean incluirFirma,
            @RequestParam(defaultValue = "Sistema SERF") String generadoPor) {
        
        String reporte = reportService.generateCustomReport(
                titulo, tipoReporte, pais, fechaInicio, fechaFin,
                filiales, incluirGraficos, incluirFirma, generadoPor
        );
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("titulo", titulo);
        response.put("reporte", reporte);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Genera un reporte consolidado de todas las filiales
     */
    @PostMapping("/consolidado")
    public ResponseEntity<Map<String, String>> generateConsolidatedReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(defaultValue = "Sistema SERF") String generadoPor) {
        
        String reporte = reportService.generateConsolidatedReport(fechaInicio, fechaFin, generadoPor);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("tipo", "Reporte Consolidado Corporativo");
        response.put("reporte", reporte);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtiene estadísticas de una filial
     */
    @GetMapping("/filial/{id}/estadisticas")
    public ResponseEntity<Map<String, String>> getFilialStatistics(@PathVariable Long id) {
        String stats = reportService.getFilialStatistics(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("estadisticas", stats);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint de prueba
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Sistema de Reportes SERF funcionando correctamente");
        response.put("version", "1.0.0");
        
        return ResponseEntity.ok(response);
    }
}
