package com.financorp.serf.patterns.structural;

/**
 * Leaf - Contenido de texto del reporte
 */
public class ReportContent extends ReportComponent {
    private String content;
    
    public ReportContent(String name, String content, int level) {
        super(name, level);
        this.content = content;
    }
    
    @Override
    public String render() {
        String indent = "  ".repeat(level - 1);
        StringBuilder sb = new StringBuilder();
        
        if (!name.isEmpty()) {
            sb.append(indent).append("• ").append(name).append("\n");
        }
        
        sb.append(indent).append(content).append("\n\n");
        
        return sb.toString();
    }
    
    @Override
    public void add(ReportComponent component) {
        throw new UnsupportedOperationException("No se puede agregar componentes a un contenido");
    }
    
    @Override
    public void remove(ReportComponent component) {
        throw new UnsupportedOperationException("No se puede remover componentes de un contenido");
    }
    
    @Override
    public ReportComponent getChild(int index) {
        throw new UnsupportedOperationException("Un contenido no tiene hijos");
    }
    
    @Override
    public int getChildCount() {
        return 0;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}
