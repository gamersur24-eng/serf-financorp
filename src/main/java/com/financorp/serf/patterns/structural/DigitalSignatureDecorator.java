package com.financorp.serf.patterns.structural;

/**
 * Decorador: Añade firma digital
 */
public class DigitalSignatureDecorator extends ReportDecorator {
    private String signerName;
    private String signerRole;
    private String signatureCode;
    
    public DigitalSignatureDecorator(Report report, String signerName, String signerRole) {
        super(report);
        this.signerName = signerName;
        this.signerRole = signerRole;
        this.signatureCode = generateSignatureCode();
    }
    
    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(super.generate());
        sb.append("\n");
        sb.append("═".repeat(70)).append("\n");
        sb.append("FIRMA DIGITAL\n");
        sb.append("═".repeat(70)).append("\n");
        sb.append("Firmante: ").append(signerName).append("\n");
        sb.append("Cargo: ").append(signerRole).append("\n");
        sb.append("Código de firma: ").append(signatureCode).append("\n");
        sb.append("Estado: ✓ VERIFICADO\n");
        sb.append("═".repeat(70)).append("\n");
        
        return sb.toString();
    }
    
    private String generateSignatureCode() {
        // Simulación de código de firma digital
        return "SERF-" + System.currentTimeMillis() % 1000000;
    }
}
