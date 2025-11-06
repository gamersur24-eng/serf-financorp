package com.financorp.serf.patterns.creational;

import java.util.HashMap;
import java.util.Map;

/**
 * PATRÓN SINGLETON - Configuración Centralizada
 * 
 * Mantiene una configuración centralizada de la aplicación que puede ser 
 * utilizada en cualquier parte del sistema.
 * 
 * Características:
 * - Única instancia en toda la aplicación
 * - Acceso global a la configuración
 * - Thread-safe (inicialización eager)
 */
public class AppConfiguration {
    
    // Instancia única (Eager initialization - thread-safe)
    private static final AppConfiguration INSTANCE = new AppConfiguration();
    
    // Configuraciones del sistema
    private final Map<String, Object> configurations;
    
    // Constructor privado para evitar instanciación externa
    private AppConfiguration() {
        this.configurations = new HashMap<>();
        loadDefaultConfigurations();
    }
    
    /**
     * Método para obtener la única instancia
     */
    public static AppConfiguration getInstance() {
        return INSTANCE;
    }
    
    /**
     * Carga configuraciones por defecto
     */
    private void loadDefaultConfigurations() {
        // Configuraciones corporativas
        configurations.put("company.name", "FinanCorp S.A.");
        configurations.put("company.headquarters", "España");
        configurations.put("system.version", "1.0.0");
        
        // Formatos de reporte por país
        configurations.put("report.format.spain", "EUR - dd/MM/yyyy");
        configurations.put("report.format.mexico", "MXN - dd/MM/yyyy");
        configurations.put("report.format.argentina", "ARS - dd/MM/yyyy");
        configurations.put("report.format.peru", "PEN - dd/MM/yyyy");
        
        // Plantillas base de reportes
        configurations.put("report.template.sales", "Reporte de Ventas Corporativo");
        configurations.put("report.template.inventory", "Reporte de Inventario");
        configurations.put("report.template.financial", "Reporte Financiero Consolidado");
        
        // Configuraciones de seguridad
        configurations.put("security.encryption.enabled", true);
        configurations.put("security.audit.enabled", true);
        
        // Configuraciones de integración
        configurations.put("integration.max.filiales", 50);
        configurations.put("integration.timeout.seconds", 30);
    }
    
    /**
     * Obtiene una configuración
     */
    public Object getConfiguration(String key) {
        return configurations.get(key);
    }
    
    /**
     * Obtiene una configuración como String
     */
    public String getConfigurationAsString(String key) {
        Object value = configurations.get(key);
        return value != null ? value.toString() : null;
    }
    
    /**
     * Establece una configuración
     */
    public void setConfiguration(String key, Object value) {
        configurations.put(key, value);
    }
    
    /**
     * Verifica si existe una configuración
     */
    public boolean hasConfiguration(String key) {
        return configurations.containsKey(key);
    }
    
    /**
     * Obtiene todas las configuraciones
     */
    public Map<String, Object> getAllConfigurations() {
        return new HashMap<>(configurations);
    }
    
    /**
     * Obtiene el formato de reporte para un país específico
     */
    public String getReportFormatForCountry(String country) {
        String key = "report.format." + country.toLowerCase();
        return getConfigurationAsString(key);
    }
}
