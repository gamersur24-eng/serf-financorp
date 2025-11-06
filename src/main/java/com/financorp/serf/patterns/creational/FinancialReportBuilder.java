package com.financorp.serf.patterns.creational;

import com.financorp.serf.patterns.structural.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * PATRÓN BUILDER - Construcción de Reportes Complejos
 * 
 * Permite construir reportes complejos paso a paso, agregando:
 * - Secciones y subsecciones
 * - Gráficos y tablas
 * - Contenido personalizado
 * - Configuración de formato
 */
public class FinancialReportBuilder {
    
    private String title;
    private String reportType;
    private String country;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ReportSection rootSection;
    private List<String> filiales;
    private boolean includeCharts;
    private boolean includeSummary;
    private String currency;
    
    // Para aplicar decoradores
    private boolean includeHeader;
    private boolean includeFooter;
    private boolean includeWatermark;
    private boolean includeSignature;
    private boolean includeAudit;
    private String generatedBy;
    private String signerName;
    private String signerRole;
    private String auditor;
    private String auditNotes;
    
    public FinancialReportBuilder() {
        this.filiales = new ArrayList<>();
        this.includeCharts = false;
        this.includeSummary = true;
        this.includeHeader = true;
        this.includeFooter = true;
    }
    
    /**
     * Establece el título del reporte
     */
    public FinancialReportBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
    
    /**
     * Establece el tipo de reporte
     */
    public FinancialReportBuilder setReportType(String reportType) {
        this.reportType = reportType;
        return this;
    }
    
    /**
     * Establece el país del reporte
     */
    public FinancialReportBuilder setCountry(String country) {
        this.country = country;
        // Obtener moneda según el país
        this.currency = getCurrencyForCountry(country);
        return this;
    }
    
    /**
     * Establece el período del reporte
     */
    public FinancialReportBuilder setPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        return this;
    }
    
    /**
     * Añade una filial al reporte
     */
    public FinancialReportBuilder addFilial(String filial) {
        this.filiales.add(filial);
        return this;
    }
    
    /**
     * Establece si incluir gráficos
     */
    public FinancialReportBuilder includeCharts(boolean include) {
        this.includeCharts = include;
        return this;
    }
    
    /**
     * Establece si incluir resumen ejecutivo
     */
    public FinancialReportBuilder includeSummary(boolean include) {
        this.includeSummary = include;
        return this;
    }
    
    /**
     * Configura decoradores de encabezado
     */
    public FinancialReportBuilder withHeader(boolean include) {
        this.includeHeader = include;
        return this;
    }
    
    /**
     * Configura decoradores de pie de página
     */
    public FinancialReportBuilder withFooter(boolean include, String generatedBy) {
        this.includeFooter = include;
        this.generatedBy = generatedBy;
        return this;
    }
    
    /**
     * Configura decoradores de marca de agua
     */
    public FinancialReportBuilder withWatermark(boolean include) {
        this.includeWatermark = include;
        return this;
    }
    
    /**
     * Configura decoradores de firma digital
     */
    public FinancialReportBuilder withDigitalSignature(boolean include, String signerName, String signerRole) {
        this.includeSignature = include;
        this.signerName = signerName;
        this.signerRole = signerRole;
        return this;
    }
    
    /**
     * Configura decoradores de auditoría
     */
    public FinancialReportBuilder withAudit(boolean include, String auditor, String notes) {
        this.includeAudit = include;
        this.auditor = auditor;
        this.auditNotes = notes;
        return this;
    }
    
    /**
     * Construye el reporte completo
     */
    public Report build() {
        // Validar campos requeridos
        if (title == null || reportType == null) {
            throw new IllegalStateException("Título y tipo de reporte son requeridos");
        }
        
        // Crear estructura del reporte usando Composite
        rootSection = new ReportSection(title, 1);
        
        // Añadir sección de información general
        addGeneralInfoSection();
        
        // Añadir resumen ejecutivo si está configurado
        if (includeSummary) {
            addExecutiveSummary();
        }
        
        // Añadir sección de datos por filial
        addFilialDataSection();
        
        // Añadir gráficos si está configurado
        if (includeCharts) {
            addChartsSection();
        }
        
        // Añadir conclusiones y recomendaciones
        addConclusionsSection();
        
        // Crear el reporte básico
        String reportContent = rootSection.render();
        Report report = new BasicReport(title, reportType, reportContent);
        
        // Aplicar decoradores según configuración
        report = applyDecorators(report);
        
        return report;
    }
    
    /**
     * Añade sección de información general
     */
    private void addGeneralInfoSection() {
        ReportSection infoSection = new ReportSection("Información General", 2);
        
        StringBuilder info = new StringBuilder();
        info.append("Tipo de Reporte: ").append(reportType).append("\n");
        info.append("País: ").append(country != null ? country : "Corporativo").append("\n");
        info.append("Moneda: ").append(currency != null ? currency : "Múltiples").append("\n");
        
        if (startDate != null && endDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            info.append("Período: ").append(startDate.format(formatter))
                .append(" - ").append(endDate.format(formatter)).append("\n");
        }
        
        info.append("Filiales incluidas: ").append(filiales.isEmpty() ? "Todas" : filiales.size()).append("\n");
        
        infoSection.add(new ReportContent("", info.toString(), 3));
        rootSection.add(infoSection);
    }
    
    /**
     * Añade resumen ejecutivo
     */
    private void addExecutiveSummary() {
        ReportSection summarySection = new ReportSection("Resumen Ejecutivo", 2);
        
        String summary = "Este reporte consolida la información financiera de las filiales de FinanCorp S.A. " +
                        "para el período especificado. Se incluyen análisis de ventas, inventario y " +
                        "rendimiento operativo con el objetivo de facilitar la toma de decisiones estratégicas.";
        
        summarySection.add(new ReportContent("", summary, 3));
        rootSection.add(summarySection);
    }
    
    /**
     * Añade sección de datos por filial
     */
    private void addFilialDataSection() {
        ReportSection dataSection = new ReportSection("Datos Consolidados", 2);
        
        // Crear tabla de ejemplo
        ReportTable table = new ReportTable("Resumen por Filial", 3);
        table.setHeaders(List.of("Filial", "Ventas", "Inventario", "Rendimiento"));
        
        if (filiales.isEmpty()) {
            table.addRow(List.of("ES-001 España", "€1,250,000", "€450,000", "85%"));
            table.addRow(List.of("MX-001 México", "$850,000", "$320,000", "78%"));
            table.addRow(List.of("AR-001 Argentina", "ARS 2,100,000", "ARS 780,000", "72%"));
        } else {
            for (String filial : filiales) {
                table.addRow(List.of(filial, "N/D", "N/D", "N/D"));
            }
        }
        
        dataSection.add(table);
        rootSection.add(dataSection);
    }
    
    /**
     * Añade sección de gráficos
     */
    private void addChartsSection() {
        ReportSection chartsSection = new ReportSection("Análisis Gráfico", 2);
        
        String chartInfo = "Los gráficos de tendencias y comparativos se generan automáticamente " +
                          "basados en los datos consolidados de todas las filiales.";
        
        chartsSection.add(new ReportContent("", chartInfo, 3));
        rootSection.add(chartsSection);
    }
    
    /**
     * Añade sección de conclusiones
     */
    private void addConclusionsSection() {
        ReportSection conclusionsSection = new ReportSection("Conclusiones y Recomendaciones", 2);
        
        String conclusions = "Basado en el análisis de los datos consolidados, se recomienda:\n" +
                           "1. Continuar monitoreando el rendimiento de las filiales\n" +
                           "2. Optimizar los niveles de inventario en función de la demanda\n" +
                           "3. Implementar estrategias de mejora en las filiales con bajo rendimiento";
        
        conclusionsSection.add(new ReportContent("", conclusions, 3));
        rootSection.add(conclusionsSection);
    }
    
    /**
     * Aplica decoradores al reporte según configuración
     */
    private Report applyDecorators(Report report) {
        Report decoratedReport = report;
        
        // Aplicar en orden específico para mejor presentación
        if (includeWatermark) {
            decoratedReport = new WatermarkDecorator(decoratedReport, "CONFIDENCIAL");
        }
        
        if (includeHeader) {
            String company = AppConfiguration.getInstance()
                    .getConfigurationAsString("company.name");
            decoratedReport = new HeaderDecorator(decoratedReport, company, 
                    "Departamento de Reportes Financieros");
        }
        
        if (includeAudit) {
            decoratedReport = new AuditDecorator(decoratedReport, auditor, auditNotes);
        }
        
        if (includeSignature) {
            decoratedReport = new DigitalSignatureDecorator(decoratedReport, 
                    signerName, signerRole);
        }
        
        if (includeFooter) {
            decoratedReport = new FooterDecorator(decoratedReport, 
                    generatedBy != null ? generatedBy : "Sistema SERF");
        }
        
        return decoratedReport;
    }
    
    /**
     * Obtiene la moneda según el país
     */
    private String getCurrencyForCountry(String country) {
        return switch (country.toLowerCase()) {
            case "españa", "spain" -> "EUR";
            case "méxico", "mexico" -> "MXN";
            case "argentina" -> "ARS";
            case "perú", "peru" -> "PEN";
            case "colombia" -> "COP";
            case "chile" -> "CLP";
            default -> "USD";
        };
    }
}
