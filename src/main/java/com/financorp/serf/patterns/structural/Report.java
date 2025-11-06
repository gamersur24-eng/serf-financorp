package com.financorp.serf.patterns.structural;

/**
 * PATRÓN DECORATOR - Interfaz base para reportes
 */
public interface Report {
    String generate();
    String getTitle();
    String getType();
}
