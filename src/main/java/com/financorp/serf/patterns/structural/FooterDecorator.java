package com.financorp.serf.patterns.structural;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Decorador: Añade pie de página con fecha y firma
 */
public class FooterDecorator extends ReportDecorator {
    private String generatedBy;
    private LocalDateTime generationDate;
    
    public FooterDecorator(Report report, String generatedBy) {
        super(report);
        this.generatedBy = generatedBy;
        this.generationDate = LocalDateTime.now();
    }
    
    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(super.generate());
        sb.append("\n");
        sb.append("─".repeat(70)).append("\n");
        sb.append("Generado por: ").append(generatedBy).append("\n");
        sb.append("Fecha y hora: ").append(formatDateTime(generationDate)).append("\n");
        sb.append("Tipo de reporte: ").append(getType()).append("\n");
        sb.append("─".repeat(70)).append("\n");
        
        return sb.toString();
    }
    
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}
