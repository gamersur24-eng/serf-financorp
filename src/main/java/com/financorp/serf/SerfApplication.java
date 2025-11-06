package com.financorp.serf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sistema Empresarial de Gestión de Reportes Financieros (SERF)
 * Aplicación principal para FinanCorp S.A.
 * 
 * @author Equipo de Desarrollo TI
 * @version 1.0.0
 */
@SpringBootApplication
public class SerfApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerfApplication.class, args);
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  SERF - Sistema Empresarial de Reportes Financieros       ║");
        System.out.println("║  FinanCorp S.A. - Versión 1.0.0                            ║");
        System.out.println("║  Servidor iniciado en: http://localhost:8080               ║");
        System.out.println("║  H2 Console: http://localhost:8080/h2-console              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
}
