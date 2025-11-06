package com.financorp.serf.patterns.creational;

import com.financorp.serf.patterns.structural.Report;

import java.time.LocalDateTime;

/**
 * PATRÓN FACTORY - Creación de Diferentes Tipos de Reportes
 * 
 * Proporciona una interfaz para crear diferentes tipos de reportes
 * sin exponer la lógica de creación al cliente:
 * - Reportes de Ventas
 * - Reportes de Inventario
 * - Reportes Financieros Consolidados
 * - Reportes por Filial
 */
public class ReportFactory {
    
    /**
     * Tipo de reportes disponibles
     */
    public enum ReportType {
        SALES("Reporte de Ventas"),
        INVENTORY("Reporte de Inventario"),
        FINANCIAL("Reporte Financiero Consolidado"),
        BRANCH("Reporte por Filial"),
        EXECUTIVE("Reporte Ejecutivo");
        
        private final String description;
        
        ReportType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Crea un reporte según el tipo especificado
     */
    public static Report createReport(ReportType type, ReportConfiguration config) {
        FinancialReportBuilder builder = new FinancialReportBuilder();
        
        // Configuración común
        builder.setCountry(config.getCountry())
               .setPeriod(config.getStartDate(), config.getEndDate())
               .withFooter(true, config.getGeneratedBy());
        
        // Añadir filiales si están especificadas
        if (config.getFiliales() != null) {
            config.getFiliales().forEach(builder::addFilial);
        }
        
        // Configuración específica según el tipo
        switch (type) {
            case SALES:
                return createSalesReport(builder, config);
            case INVENTORY:
                return createInventoryReport(builder, config);
            case FINANCIAL:
                return createFinancialReport(builder, config);
            case BRANCH:
                return createBranchReport(builder, config);
            case EXECUTIVE:
                return createExecutiveReport(builder, config);
            default:
                throw new IllegalArgumentException("Tipo de reporte no soportado: " + type);
        }
    }
    
    /**
     * Crea un reporte de ventas
     */
    private static Report createSalesReport(FinancialReportBuilder builder, ReportConfiguration config) {
        return builder
                .setTitle("Reporte de Ventas - FinanCorp S.A.")
                .setReportType(ReportType.SALES.getDescription())
                .includeCharts(true)
                .includeSummary(true)
                .withHeader(true)
                .withWatermark(config.isConfidential())
                .build();
    }
    
    /**
     * Crea un reporte de inventario
     */
    private static Report createInventoryReport(FinancialReportBuilder builder, ReportConfiguration config) {
        return builder
                .setTitle("Reporte de Inventario - FinanCorp S.A.")
                .setReportType(ReportType.INVENTORY.getDescription())
                .includeCharts(false)
                .includeSummary(true)
                .withHeader(true)
                .withWatermark(config.isConfidential())
                .build();
    }
    
    /**
     * Crea un reporte financiero consolidado
     */
    private static Report createFinancialReport(FinancialReportBuilder builder, ReportConfiguration config) {
        return builder
                .setTitle("Reporte Financiero Consolidado - FinanCorp S.A.")
                .setReportType(ReportType.FINANCIAL.getDescription())
                .includeCharts(true)
                .includeSummary(true)
                .withHeader(true)
                .withWatermark(true)
                .withDigitalSignature(config.isRequireSignature(), 
                        config.getSignerName(), 
                        config.getSignerRole())
                .withAudit(config.isRequireAudit(), 
                        config.getAuditor(), 
                        config.getAuditNotes())
                .build();
    }
    
    /**
     * Crea un reporte por filial
     */
    private static Report createBranchReport(FinancialReportBuilder builder, ReportConfiguration config) {
        String filialName = config.getFiliales() != null && !config.getFiliales().isEmpty() 
                ? config.getFiliales().get(0) 
                : "Filial";
        
        return builder
                .setTitle("Reporte de Filial: " + filialName)
                .setReportType(ReportType.BRANCH.getDescription())
                .includeCharts(true)
                .includeSummary(false)
                .withHeader(true)
                .withWatermark(config.isConfidential())
                .build();
    }
    
    /**
     * Crea un reporte ejecutivo
     */
    private static Report createExecutiveReport(FinancialReportBuilder builder, ReportConfiguration config) {
        return builder
                .setTitle("Reporte Ejecutivo - FinanCorp S.A.")
                .setReportType(ReportType.EXECUTIVE.getDescription())
                .includeCharts(true)
                .includeSummary(true)
                .withHeader(true)
                .withWatermark(true)
                .withDigitalSignature(true, 
                        config.getSignerName() != null ? config.getSignerName() : "CEO FinanCorp", 
                        config.getSignerRole() != null ? config.getSignerRole() : "Chief Executive Officer")
                .build();
    }
    
    /**
     * Crea un reporte simple con configuración mínima
     */
    public static Report createSimpleReport(ReportType type, String country, String generatedBy) {
        ReportConfiguration config = ReportConfiguration.builder()
                .country(country)
                .startDate(LocalDateTime.now().minusMonths(1))
                .endDate(LocalDateTime.now())
                .generatedBy(generatedBy)
                .build();
        
        return createReport(type, config);
    }
}
