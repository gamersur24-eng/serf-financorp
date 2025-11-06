package com.financorp.serf.patterns.structural;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite - Sección que puede contener subsecciones
 */
public class ReportSection extends ReportComponent {
    private List<ReportComponent> children;
    
    public ReportSection(String name, int level) {
        super(name, level);
        this.children = new ArrayList<>();
    }
    
    @Override
    public void add(ReportComponent component) {
        children.add(component);
    }
    
    @Override
    public void remove(ReportComponent component) {
        children.remove(component);
    }
    
    @Override
    public ReportComponent getChild(int index) {
        return children.get(index);
    }
    
    @Override
    public int getChildCount() {
        return children.size();
    }
    
    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        
        // Renderizar título de sección con indentación según nivel
        String indent = getIndent();
        sb.append(indent).append("═".repeat(50 - level * 2)).append("\n");
        sb.append(indent).append(getHeaderPrefix()).append(name).append("\n");
        sb.append(indent).append("═".repeat(50 - level * 2)).append("\n\n");
        
        // Renderizar todos los hijos
        for (ReportComponent child : children) {
            sb.append(child.render());
        }
        
        return sb.toString();
    }
    
    private String getIndent() {
        return "  ".repeat(level - 1);
    }
    
    private String getHeaderPrefix() {
        return switch (level) {
            case 1 -> "# ";
            case 2 -> "## ";
            case 3 -> "### ";
            default -> "#### ";
        };
    }
    
    /**
     * Obtiene todos los hijos
     */
    public List<ReportComponent> getChildren() {
        return new ArrayList<>(children);
    }
}
