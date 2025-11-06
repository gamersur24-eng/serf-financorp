package com.financorp.serf.patterns.structural;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Decorador: Añade notas de auditoría
 */
public class AuditDecorator extends ReportDecorator {
    private String auditNotes;
    private String auditor;
    
    public AuditDecorator(Report report, String auditor, String auditNotes) {
        super(report);
        this.auditor = auditor;
        this.auditNotes = auditNotes;
    }
    
    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(super.generate());
        sb.append("\n");
        sb.append("┌").append("─".repeat(68)).append("┐\n");
        sb.append("│ NOTAS DE AUDITORÍA").append(" ".repeat(49)).append("│\n");
        sb.append("├").append("─".repeat(68)).append("┤\n");
        sb.append("│ Auditor: ").append(padRight(auditor, 57)).append("│\n");
        sb.append("│ Notas: ").append(padRight(auditNotes, 59)).append("│\n");
        sb.append("│ Fecha: ").append(padRight(formatDateTime(LocalDateTime.now()), 59)).append("│\n");
        sb.append("└").append("─".repeat(68)).append("┘\n");
        
        return sb.toString();
    }
    
    private String padRight(String s, int n) {
        if (s.length() > n) {
            return s.substring(0, n - 3) + "...";
        }
        return String.format("%-" + n + "s", s);
    }
    
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}
