package com.financorp.serf.patterns.creational;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase de configuración para la creación de reportes
 */
public class ReportConfiguration {
    private String country;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> filiales;
    private boolean confidential;
    private boolean requireSignature;
    private boolean requireAudit;
    private String generatedBy;
    private String signerName;
    private String signerRole;
    private String auditor;
    private String auditNotes;
    
    private ReportConfiguration(Builder builder) {
        this.country = builder.country;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.filiales = builder.filiales;
        this.confidential = builder.confidential;
        this.requireSignature = builder.requireSignature;
        this.requireAudit = builder.requireAudit;
        this.generatedBy = builder.generatedBy;
        this.signerName = builder.signerName;
        this.signerRole = builder.signerRole;
        this.auditor = builder.auditor;
        this.auditNotes = builder.auditNotes;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    // Getters
    public String getCountry() { return country; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public List<String> getFiliales() { return filiales; }
    public boolean isConfidential() { return confidential; }
    public boolean isRequireSignature() { return requireSignature; }
    public boolean isRequireAudit() { return requireAudit; }
    public String getGeneratedBy() { return generatedBy; }
    public String getSignerName() { return signerName; }
    public String getSignerRole() { return signerRole; }
    public String getAuditor() { return auditor; }
    public String getAuditNotes() { return auditNotes; }
    
    /**
     * Builder para ReportConfiguration
     */
    public static class Builder {
        private String country;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private List<String> filiales;
        private boolean confidential = false;
        private boolean requireSignature = false;
        private boolean requireAudit = false;
        private String generatedBy;
        private String signerName;
        private String signerRole;
        private String auditor;
        private String auditNotes;
        
        public Builder country(String country) {
            this.country = country;
            return this;
        }
        
        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }
        
        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }
        
        public Builder filiales(List<String> filiales) {
            this.filiales = filiales;
            return this;
        }
        
        public Builder confidential(boolean confidential) {
            this.confidential = confidential;
            return this;
        }
        
        public Builder requireSignature(boolean requireSignature) {
            this.requireSignature = requireSignature;
            return this;
        }
        
        public Builder requireAudit(boolean requireAudit) {
            this.requireAudit = requireAudit;
            return this;
        }
        
        public Builder generatedBy(String generatedBy) {
            this.generatedBy = generatedBy;
            return this;
        }
        
        public Builder signerName(String signerName) {
            this.signerName = signerName;
            return this;
        }
        
        public Builder signerRole(String signerRole) {
            this.signerRole = signerRole;
            return this;
        }
        
        public Builder auditor(String auditor) {
            this.auditor = auditor;
            return this;
        }
        
        public Builder auditNotes(String auditNotes) {
            this.auditNotes = auditNotes;
            return this;
        }
        
        public ReportConfiguration build() {
            return new ReportConfiguration(this);
        }
    }
}
