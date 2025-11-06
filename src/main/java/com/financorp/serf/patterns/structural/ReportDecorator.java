package com.financorp.serf.patterns.structural;

/**
 * Decorador abstracto base
 */
public abstract class ReportDecorator implements Report {
    protected Report decoratedReport;
    
    public ReportDecorator(Report report) {
        this.decoratedReport = report;
    }
    
    @Override
    public String generate() {
        return decoratedReport.generate();
    }
    
    @Override
    public String getTitle() {
        return decoratedReport.getTitle();
    }
    
    @Override
    public String getType() {
        return decoratedReport.getType();
    }
}
