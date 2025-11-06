package com.financorp.serf.patterns.structural;

import java.util.ArrayList;
import java.util.List;

/**
 * Leaf - Tabla en el reporte
 */
public class ReportTable extends ReportComponent {
    private List<String> headers;
    private List<List<String>> rows;
    
    public ReportTable(String name, int level) {
        super(name, level);
        this.headers = new ArrayList<>();
        this.rows = new ArrayList<>();
    }
    
    public void setHeaders(List<String> headers) {
        this.headers = new ArrayList<>(headers);
    }
    
    public void addRow(List<String> row) {
        this.rows.add(new ArrayList<>(row));
    }
    
    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        String indent = "  ".repeat(level - 1);
        
        if (!name.isEmpty()) {
            sb.append(indent).append("📊 ").append(name).append("\n");
        }
        
        // Calcular anchos de columnas
        int[] columnWidths = calculateColumnWidths();
        
        // Renderizar encabezados
        sb.append(indent).append("┌");
        for (int i = 0; i < headers.size(); i++) {
            sb.append("─".repeat(columnWidths[i] + 2));
            if (i < headers.size() - 1) sb.append("┬");
        }
        sb.append("┐\n");
        
        sb.append(indent).append("│");
        for (int i = 0; i < headers.size(); i++) {
            sb.append(" ").append(padRight(headers.get(i), columnWidths[i])).append(" │");
        }
        sb.append("\n");
        
        sb.append(indent).append("├");
        for (int i = 0; i < headers.size(); i++) {
            sb.append("─".repeat(columnWidths[i] + 2));
            if (i < headers.size() - 1) sb.append("┼");
        }
        sb.append("┤\n");
        
        // Renderizar filas
        for (List<String> row : rows) {
            sb.append(indent).append("│");
            for (int i = 0; i < row.size(); i++) {
                sb.append(" ").append(padRight(row.get(i), columnWidths[i])).append(" │");
            }
            sb.append("\n");
        }
        
        sb.append(indent).append("└");
        for (int i = 0; i < headers.size(); i++) {
            sb.append("─".repeat(columnWidths[i] + 2));
            if (i < headers.size() - 1) sb.append("┴");
        }
        sb.append("┘\n\n");
        
        return sb.toString();
    }
    
    private int[] calculateColumnWidths() {
        int[] widths = new int[headers.size()];
        
        // Inicializar con anchos de encabezados
        for (int i = 0; i < headers.size(); i++) {
            widths[i] = headers.get(i).length();
        }
        
        // Verificar anchos de datos
        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                widths[i] = Math.max(widths[i], row.get(i).length());
            }
        }
        
        return widths;
    }
    
    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
    
    @Override
    public void add(ReportComponent component) {
        throw new UnsupportedOperationException("No se puede agregar componentes a una tabla");
    }
    
    @Override
    public void remove(ReportComponent component) {
        throw new UnsupportedOperationException("No se puede remover componentes de una tabla");
    }
    
    @Override
    public ReportComponent getChild(int index) {
        throw new UnsupportedOperationException("Una tabla no tiene hijos");
    }
    
    @Override
    public int getChildCount() {
        return 0;
    }
}
