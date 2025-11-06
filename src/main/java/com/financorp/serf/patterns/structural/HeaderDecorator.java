package com.financorp.serf.patterns.structural;

/**
 * Decorador: Añade encabezado corporativo
 */
public class HeaderDecorator extends ReportDecorator {
    private String companyName;
    private String department;
    
    public HeaderDecorator(Report report, String companyName, String department) {
        super(report);
        this.companyName = companyName;
        this.department = department;
    }
    
    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
        sb.append("║                       ").append(centerText(companyName, 38)).append("                       ║\n");
        sb.append("║                       ").append(centerText(department, 38)).append("                       ║\n");
        sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
        sb.append(super.generate());
        
        return sb.toString();
    }
    
    private String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
}
