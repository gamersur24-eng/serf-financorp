package com.financorp.serf.patterns.structural;

/**
 * Reporte concreto básico
 */
public class BasicReport implements Report {
    private String title;
    private String type;
    private String content;
    
    public BasicReport(String title, String type, String content) {
        this.title = title;
        this.type = type;
        this.content = content;
    }
    
    @Override
    public String generate() {
        return content;
    }
    
    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public String getType() {
        return type;
    }
}
