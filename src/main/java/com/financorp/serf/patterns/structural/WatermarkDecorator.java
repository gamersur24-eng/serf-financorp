package com.financorp.serf.patterns.structural;

/**
 * Decorador: Añade marca de agua (confidencialidad)
 */
public class WatermarkDecorator extends ReportDecorator {
    private String watermarkText;
    
    public WatermarkDecorator(Report report, String watermarkText) {
        super(report);
        this.watermarkText = watermarkText;
    }
    
    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("╔═══════════════════════════════════════════════════════╗\n");
        sb.append("║  ").append(watermarkText.toUpperCase()).append("  ║\n");
        sb.append("╚═══════════════════════════════════════════════════════╝\n\n");
        sb.append(super.generate());
        sb.append("\n╚═══════════════════════════════════════════════════════╝\n");
        sb.append("║  ").append(watermarkText.toUpperCase()).append("  ║\n");
        sb.append("╚═══════════════════════════════════════════════════════╝\n");
        
        return sb.toString();
    }
}
