package com.financorp.serf.patterns.structural;

/**
 * PATRÓN COMPOSITE - Componente abstracto base
 * Permite crear reportes con estructura jerárquica de secciones y subsecciones
 */
public abstract class ReportComponent {
    protected String name;
    protected int level; // Nivel de jerarquía (1, 2, 3, etc.)
    
    public ReportComponent(String name, int level) {
        this.name = name;
        this.level = level;
    }
    
    public String getName() {
        return name;
    }
    
    public int getLevel() {
        return level;
    }
    
    // Operaciones que todos los componentes deben implementar
    public abstract String render();
    public abstract void add(ReportComponent component);
    public abstract void remove(ReportComponent component);
    public abstract ReportComponent getChild(int index);
    public abstract int getChildCount();
}
