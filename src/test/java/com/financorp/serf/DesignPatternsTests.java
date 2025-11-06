package com.financorp.serf;

import com.financorp.serf.patterns.creational.AppConfiguration;
import com.financorp.serf.patterns.creational.FinancialReportBuilder;
import com.financorp.serf.patterns.creational.ReportConfiguration;
import com.financorp.serf.patterns.creational.ReportFactory;
import com.financorp.serf.patterns.structural.Report;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para los patrones de diseño implementados
 */
@SpringBootTest
class DesignPatternsTests {
    
    /**
     * Test del patrón Singleton
     */
    @Test
    void testSingletonPattern() {
        // Obtener dos instancias
        AppConfiguration config1 = AppConfiguration.getInstance();
        AppConfiguration config2 = AppConfiguration.getInstance();
        
        // Verificar que son la misma instancia
        assertSame(config1, config2, "Las instancias deben ser la misma (Singleton)");
        
        // Verificar configuraciones por defecto
        assertNotNull(config1.getConfiguration("company.name"));
        assertEquals("FinanCorp S.A.", config1.getConfigurationAsString("company.name"));
        
        // Modificar configuración y verificar que se refleja en ambas instancias
        config1.setConfiguration("test.key", "test.value");
        assertEquals("test.value", config2.getConfigurationAsString("test.key"));
    }
    
    /**
     * Test del patrón Builder
     */
    @Test
    void testBuilderPattern() {
        // Crear un reporte usando el Builder
        Report report = new FinancialReportBuilder()
                .setTitle("Reporte de Prueba")
                .setReportType("Test Report")
                .setCountry("España")
                .setPeriod(LocalDateTime.now().minusMonths(1), LocalDateTime.now())
                .addFilial("ES-001")
                .addFilial("ES-002")
                .includeCharts(true)
                .includeSummary(true)
                .withHeader(true)
                .withFooter(true, "Test User")
                .build();
        
        assertNotNull(report);
        assertEquals("Reporte de Prueba", report.getTitle());
        assertEquals("Test Report", report.getType());
        
        String generated = report.generate();
        assertNotNull(generated);
        assertTrue(generated.length() > 0);
        assertTrue(generated.contains("Reporte de Prueba"));
    }
    
    /**
     * Test del patrón Factory
     */
    @Test
    void testFactoryPattern() {
        // Configurar el reporte
        ReportConfiguration config = ReportConfiguration.builder()
                .country("México")
                .startDate(LocalDateTime.now().minusMonths(1))
                .endDate(LocalDateTime.now())
                .generatedBy("Test User")
                .build();
        
        // Crear diferentes tipos de reportes usando Factory
        Report salesReport = ReportFactory.createReport(
                ReportFactory.ReportType.SALES, 
                config
        );
        
        Report inventoryReport = ReportFactory.createReport(
                ReportFactory.ReportType.INVENTORY, 
                config
        );
        
        Report financialReport = ReportFactory.createReport(
                ReportFactory.ReportType.FINANCIAL, 
                config
        );
        
        // Verificar que los reportes se crearon correctamente
        assertNotNull(salesReport);
        assertNotNull(inventoryReport);
        assertNotNull(financialReport);
        
        assertEquals(ReportFactory.ReportType.SALES.getDescription(), salesReport.getType());
        assertEquals(ReportFactory.ReportType.INVENTORY.getDescription(), inventoryReport.getType());
        assertEquals(ReportFactory.ReportType.FINANCIAL.getDescription(), financialReport.getType());
    }
    
    /**
     * Test del Factory Pattern con método simplificado
     */
    @Test
    void testSimpleFactoryMethod() {
        Report report = ReportFactory.createSimpleReport(
                ReportFactory.ReportType.EXECUTIVE,
                "Argentina",
                "Test User"
        );
        
        assertNotNull(report);
        assertNotNull(report.generate());
        assertTrue(report.generate().contains("Argentina") || 
                   report.generate().contains("Corporativo"));
    }
    
    /**
     * Test de generación completa de reporte con todos los decoradores
     */
    @Test
    void testCompleteReportGeneration() {
        Report report = new FinancialReportBuilder()
                .setTitle("Reporte Completo de Prueba")
                .setReportType("Complete Test Report")
                .setCountry("Perú")
                .setPeriod(LocalDateTime.now().minusMonths(3), LocalDateTime.now())
                .addFilial("PE-001")
                .includeCharts(true)
                .includeSummary(true)
                .withHeader(true)
                .withFooter(true, "Administrador")
                .withWatermark(true)
                .withDigitalSignature(true, "Director Financiero", "CFO")
                .withAudit(true, "Auditor Interno", "Revisión completa del reporte")
                .build();
        
        String generated = report.generate();
        
        assertNotNull(generated);
        assertTrue(generated.contains("FinanCorp"));
        assertTrue(generated.contains("CONFIDENCIAL"));
        assertTrue(generated.contains("FIRMA DIGITAL"));
        assertTrue(generated.contains("AUDITORÍA"));
    }
    
    /**
     * Test de configuración por país
     */
    @Test
    void testCountryConfiguration() {
        AppConfiguration config = AppConfiguration.getInstance();
        
        assertEquals("EUR - dd/MM/yyyy", config.getReportFormatForCountry("spain"));
        assertEquals("MXN - dd/MM/yyyy", config.getReportFormatForCountry("mexico"));
        assertEquals("ARS - dd/MM/yyyy", config.getReportFormatForCountry("argentina"));
        assertEquals("PEN - dd/MM/yyyy", config.getReportFormatForCountry("peru"));
    }
}
