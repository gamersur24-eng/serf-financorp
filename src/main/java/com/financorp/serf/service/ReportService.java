package com.financorp.serf.service;

import com.financorp.serf.model.Filial;
import com.financorp.serf.model.Producto;
import com.financorp.serf.model.Venta;
import com.financorp.serf.patterns.creational.AppConfiguration;
import com.financorp.serf.patterns.creational.FinancialReportBuilder;
import com.financorp.serf.patterns.creational.ReportConfiguration;
import com.financorp.serf.patterns.creational.ReportFactory;
import com.financorp.serf.patterns.structural.Report;
import com.financorp.serf.repository.FilialRepository;
import com.financorp.serf.repository.ProductoRepository;
import com.financorp.serf.repository.VentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la generación de reportes financieros
 * Integra todos los patrones de diseño implementados
 */
@Service
public class ReportService {
    
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    
    @Autowired
    private FilialRepository filialRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private VentaRepository ventaRepository;
    
    /**
     * Genera un reporte usando Factory Pattern
     */
    public String generateReport(ReportFactory.ReportType reportType, 
                                 String country, 
                                 LocalDateTime startDate, 
                                 LocalDateTime endDate,
                                 String generatedBy) {
        
        logger.info("Generando reporte tipo: {} para país: {}", reportType, country);
        
        // Usar el patrón Singleton para obtener la configuración
        AppConfiguration config = AppConfiguration.getInstance();
        logger.debug("Configuración del sistema: {}", config.getConfigurationAsString("company.name"));
        
        // Crear configuración del reporte
        ReportConfiguration reportConfig = ReportConfiguration.builder()
                .country(country)
                .startDate(startDate)
                .endDate(endDate)
                .generatedBy(generatedBy)
                .confidential(true)
                .build();
        
        // Usar Factory Pattern para crear el reporte
        Report report = ReportFactory.createReport(reportType, reportConfig);
        
        // Generar y retornar el reporte
        String generatedReport = report.generate();
        logger.info("Reporte generado exitosamente");
        
        return generatedReport;
    }
    
    /**
     * Genera un reporte personalizado usando Builder Pattern
     */
    public String generateCustomReport(String title,
                                       String reportType,
                                       String country,
                                       LocalDateTime startDate,
                                       LocalDateTime endDate,
                                       List<String> filiales,
                                       boolean includeCharts,
                                       boolean includeSignature,
                                       String generatedBy) {
        
        logger.info("Generando reporte personalizado: {}", title);
        
        // Usar Builder Pattern para construcción paso a paso
        FinancialReportBuilder builder = new FinancialReportBuilder();
        
        Report report = builder
                .setTitle(title)
                .setReportType(reportType)
                .setCountry(country)
                .setPeriod(startDate, endDate)
                .includeCharts(includeCharts)
                .includeSummary(true)
                .withHeader(true)
                .withFooter(true, generatedBy)
                .withWatermark(true)
                .withDigitalSignature(includeSignature, "Director Financiero", "CFO")
                .build();
        
        // Añadir filiales si se especificaron
        if (filiales != null && !filiales.isEmpty()) {
            filiales.forEach(builder::addFilial);
        }
        
        String generatedReport = report.generate();
        logger.info("Reporte personalizado generado exitosamente");
        
        return generatedReport;
    }
    
    /**
     * Genera un reporte consolidado de todas las filiales
     */
    public String generateConsolidatedReport(LocalDateTime startDate, 
                                             LocalDateTime endDate,
                                             String generatedBy) {
        
        logger.info("Generando reporte consolidado");
        
        // Obtener todas las filiales activas
        List<Filial> filialesActivas = filialRepository.findByActivaTrue();
        List<String> filialNames = filialesActivas.stream()
                .map(f -> f.getCodigo() + " - " + f.getNombre())
                .collect(Collectors.toList());
        
        // Configurar reporte
        ReportConfiguration config = ReportConfiguration.builder()
                .country("Corporativo")
                .startDate(startDate)
                .endDate(endDate)
                .filiales(filialNames)
                .confidential(true)
                .requireSignature(true)
                .requireAudit(true)
                .generatedBy(generatedBy)
                .signerName("CEO FinanCorp")
                .signerRole("Chief Executive Officer")
                .auditor("Auditor Interno")
                .auditNotes("Reporte revisado y aprobado según normativas corporativas")
                .build();
        
        // Generar usando Factory
        Report report = ReportFactory.createReport(
                ReportFactory.ReportType.FINANCIAL, 
                config
        );
        
        return report.generate();
    }
    
    /**
     * Obtiene estadísticas de una filial específica
     */
    public String getFilialStatistics(Long filialId) {
        Filial filial = filialRepository.findById(filialId)
                .orElseThrow(() -> new RuntimeException("Filial no encontrada"));
        
        List<Producto> productos = productoRepository.findByFilialId(filialId);
        List<Venta> ventas = ventaRepository.findByFilialId(filialId);
        
        Double totalVentas = ventaRepository.getTotalVentasByFilial(filialId);
        
        StringBuilder stats = new StringBuilder();
        stats.append("═══════════════════════════════════════════════════\n");
        stats.append("  ESTADÍSTICAS DE FILIAL\n");
        stats.append("═══════════════════════════════════════════════════\n\n");
        stats.append("Código: ").append(filial.getCodigo()).append("\n");
        stats.append("Nombre: ").append(filial.getNombre()).append("\n");
        stats.append("País: ").append(filial.getPais()).append("\n");
        stats.append("Ciudad: ").append(filial.getCiudad()).append("\n\n");
        stats.append("Total de productos: ").append(productos.size()).append("\n");
        stats.append("Total de ventas: ").append(ventas.size()).append("\n");
        stats.append("Monto total vendido: ").append(filial.getMoneda()).append(" ")
             .append(totalVentas != null ? totalVentas : 0).append("\n");
        stats.append("═══════════════════════════════════════════════════\n");
        
        return stats.toString();
    }
}
