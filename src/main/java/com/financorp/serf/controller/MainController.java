package com.financorp.serf.controller;

import com.financorp.serf.patterns.creational.AppConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador principal con información del sistema
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MainController {
    
    @GetMapping
    public Map<String, Object> home() {
        AppConfiguration config = AppConfiguration.getInstance();
        
        Map<String, Object> response = new HashMap<>();
        response.put("nombre", config.getConfigurationAsString("company.name"));
        response.put("sistema", "SERF - Sistema Empresarial de Reportes Financieros");
        response.put("version", config.getConfigurationAsString("system.version"));
        response.put("estado", "Operativo");
        response.put("mensaje", "Bienvenido al Sistema SERF");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("filiales", "/api/filiales");
        endpoints.put("productos", "/api/productos");
        endpoints.put("ventas", "/api/ventas");
        endpoints.put("reportes", "/api/reportes");
        response.put("endpoints", endpoints);
        
        return response;
    }
    
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "SERF");
        return response;
    }
    
    @GetMapping("/config")
    public Map<String, Object> getConfig() {
        AppConfiguration config = AppConfiguration.getInstance();
        return config.getAllConfigurations();
    }
}
