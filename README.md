# 📊 SERF - Sistema Empresarial de Gestión de Reportes Financieros

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red)
![License](https://img.shields.io/badge/License-MIT-blue)

## 📋 Descripción

Sistema web para la gestión de reportes financieros de **FinanCorp S.A.**, una corporación multinacional dedicada a la importación y venta de productos tecnológicos. El sistema integra datos de ventas e inventarios de múltiples filiales y genera reportes financieros consolidados automáticamente.

### 🎯 Características Principales

- ✅ Integración de datos de ventas e inventarios de todas las filiales
- ✅ Registro de productos importados y seguimiento de ventas
- ✅ Generación automática de reportes financieros corporativos
- ✅ Sistema escalable y adaptable a nuevas filiales
- ✅ Implementación de **5 patrones de diseño** (Creacionales y Estructurales)

---

## 🏗️ Patrones de Diseño Implementados

### 1️⃣ **Singleton** (Creacional)
**Ubicación:** `com.financorp.serf.patterns.creational.AppConfiguration`

**Propósito:** Mantener una configuración centralizada de la aplicación que puede ser utilizada en cualquier parte del sistema.

**Características:**
- Única instancia en toda la aplicación
- Acceso global a la configuración
- Thread-safe (inicialización eager)
- Configuraciones por país y plantillas de reportes

```java
AppConfiguration config = AppConfiguration.getInstance();
String format = config.getReportFormatForCountry("spain");
```

### 2️⃣ **Builder** (Creacional)
**Ubicación:** `com.financorp.serf.patterns.creational.FinancialReportBuilder`

**Propósito:** Construir reportes complejos paso a paso, agregando secciones, gráficos y tablas.

**Características:**
- Construcción paso a paso de reportes
- Configuración flexible y fluida
- Validación de campos requeridos

```java
Report report = new FinancialReportBuilder()
    .setTitle("Reporte de Ventas")
    .setCountry("España")
    .includeCharts(true)
    .withDigitalSignature(true, "CFO", "Director Financiero")
    .build();
```

### 3️⃣ **Factory** (Creacional)
**Ubicación:** `com.financorp.serf.patterns.creational.ReportFactory`

**Propósito:** Ofrecer una interfaz simple para generar diferentes tipos de reportes listos para ser enviados.

**Características:**
- Creación de 5 tipos diferentes de reportes
- Encapsulación de la lógica de creación
- Configuración predeterminada por tipo

```java
Report report = ReportFactory.createReport(
    ReportType.SALES, 
    configuration
);
```

### 4️⃣ **Composite** (Estructural)
**Ubicación:** `com.financorp.serf.patterns.structural.ReportComponent`

**Propósito:** Permitir estructura jerárquica de reportes (secciones y subsecciones).

**Características:**
- Árbol de componentes (secciones, contenido, tablas)
- Tratamiento uniforme de componentes simples y compuestos
- Renderizado recursivo

```java
ReportSection section = new ReportSection("Ventas", 1);
section.add(new ReportContent("Detalles", "...", 2));
section.add(new ReportTable("Resumen", 2));
```

### 5️⃣ **Decorator** (Estructural)
**Ubicación:** `com.financorp.serf.patterns.structural.ReportDecorator`

**Propósito:** Añadir características extra a los reportes sin modificar su estructura original.

**Características:**
- Decoradores: Header, Footer, Watermark, Digital Signature, Audit
- Composición dinámica de funcionalidades
- No modifica el reporte original

```java
Report decorated = new HeaderDecorator(report, "FinanCorp", "Finanzas");
decorated = new FooterDecorator(decorated, "Usuario");
decorated = new WatermarkDecorator(decorated, "CONFIDENCIAL");
```

---

## 🚀 Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| Java | 17 | Lenguaje base |
| Spring Boot | 3.1.5 | Framework principal |
| Spring Data JPA | 3.1.5 | Persistencia de datos |
| H2 Database | 2.x | Base de datos en memoria |
| Maven | 4.0.0 | Gestión de dependencias |
| Lombok | Latest | Reducción de boilerplate |
| JUnit 5 | Latest | Pruebas unitarias |

---

## 📁 Estructura del Proyecto

```
serf-project/
├── src/
│   ├── main/
│   │   ├── java/com/financorp/serf/
│   │   │   ├── SerfApplication.java          # Clase principal
│   │   │   ├── config/                        # Configuraciones
│   │   │   ├── controller/                    # Controladores REST
│   │   │   │   ├── MainController.java
│   │   │   │   ├── ReportController.java
│   │   │   │   ├── FilialController.java
│   │   │   │   ├── ProductoController.java
│   │   │   │   └── VentaController.java
│   │   │   ├── model/                         # Entidades JPA
│   │   │   │   ├── Filial.java
│   │   │   │   ├── Producto.java
│   │   │   │   └── Venta.java
│   │   │   ├── repository/                    # Repositorios
│   │   │   ├── service/                       # Servicios de negocio
│   │   │   └── patterns/                      # 🎨 PATRONES DE DISEÑO
│   │   │       ├── creational/
│   │   │       │   ├── AppConfiguration.java  # ⭐ Singleton
│   │   │       │   ├── FinancialReportBuilder.java  # ⭐ Builder
│   │   │       │   └── ReportFactory.java     # ⭐ Factory
│   │   │       └── structural/
│   │   │           ├── ReportComponent.java   # ⭐ Composite
│   │   │           └── ReportDecorator.java   # ⭐ Decorator
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/financorp/serf/
│           └── DesignPatternsTests.java       # 🧪 Pruebas
├── pom.xml
└── README.md
```

---

## ⚙️ Instalación y Configuración

### Requisitos Previos
- ☕ Java JDK 17 o superior
- 📦 Maven 3.6 o superior
- 💻 IDE recomendado: IntelliJ IDEA Community Edition

### Paso 1: Clonar el Repositorio
```bash
git clone <URL_DEL_REPOSITORIO>
cd serf-project
```

### Paso 2: Compilar el Proyecto
```bash
mvn clean install
```

### Paso 3: Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

O desde IntelliJ IDEA:
1. Abrir el proyecto
2. Ejecutar la clase `SerfApplication.java`

### Paso 4: Verificar la Instalación
Abre tu navegador en: `http://localhost:8080/api`

---

## 🧪 Ejecutar Pruebas Unitarias

```bash
mvn test
```

Las pruebas incluyen:
- ✅ Test del patrón Singleton
- ✅ Test del patrón Builder
- ✅ Test del patrón Factory
- ✅ Test de integración de patrones
- ✅ Test de generación completa de reportes

---

## 📡 API REST - Endpoints Principales

### 🏠 Principal
```http
GET /api                      # Información del sistema
GET /api/health              # Estado del servicio
GET /api/config              # Configuración actual
```

### 📊 Reportes
```http
POST /api/reportes/generar
  ?tipo=SALES
  &pais=España
  &fechaInicio=2024-01-01T00:00:00
  &fechaFin=2024-12-31T23:59:59
  &generadoPor=Admin

POST /api/reportes/personalizado
POST /api/reportes/consolidado
GET  /api/reportes/filial/{id}/estadisticas
```

### 🏢 Filiales
```http
GET    /api/filiales                # Listar todas
GET    /api/filiales/activas        # Listar activas
GET    /api/filiales/{id}           # Obtener por ID
POST   /api/filiales                # Crear nueva
PUT    /api/filiales/{id}           # Actualizar
DELETE /api/filiales/{id}           # Eliminar
```

### 📦 Productos
```http
GET    /api/productos               # Listar todos
GET    /api/productos/{id}          # Obtener por ID
GET    /api/productos/sku/{sku}     # Buscar por SKU
GET    /api/productos/bajo-stock    # Productos con bajo stock
POST   /api/productos               # Crear nuevo
PUT    /api/productos/{id}          # Actualizar
PATCH  /api/productos/{id}/stock    # Actualizar stock
DELETE /api/productos/{id}          # Eliminar
```

### 💰 Ventas
```http
GET    /api/ventas                  # Listar todas
GET    /api/ventas/{id}             # Obtener por ID
GET    /api/ventas/filial/{id}      # Ventas por filial
POST   /api/ventas                  # Registrar venta
PUT    /api/ventas/{id}             # Actualizar
DELETE /api/ventas/{id}             # Eliminar
```

---

## 📖 Ejemplos de Uso

### Ejemplo 1: Generar Reporte de Ventas
```bash
curl -X POST "http://localhost:8080/api/reportes/generar" \
  -d "tipo=SALES" \
  -d "pais=España" \
  -d "fechaInicio=2024-01-01T00:00:00" \
  -d "fechaFin=2024-12-31T23:59:59" \
  -d "generadoPor=Administrador"
```

### Ejemplo 2: Crear una Filial
```bash
curl -X POST "http://localhost:8080/api/filiales" \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "ES-001",
    "nombre": "Filial Madrid",
    "pais": "España",
    "ciudad": "Madrid",
    "moneda": "EUR",
    "responsable": "Juan Pérez"
  }'
```

### Ejemplo 3: Listar Productos con Bajo Stock
```bash
curl -X GET "http://localhost:8080/api/productos/bajo-stock"
```

---

## 🗄️ Base de Datos

El sistema utiliza **H2 Database** en memoria para desarrollo y pruebas.

### Acceso a H2 Console
1. URL: `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:mem:serfdb`
3. Usuario: `sa`
4. Contraseña: *(vacío)*

---

## 🎨 Diagramas UML

### Diagrama de Clases - Patrones de Diseño

```
┌─────────────────────┐
│ AppConfiguration    │◄────── Singleton
│ (Singleton)         │
├─────────────────────┤
│ - instance          │
│ - configurations    │
├─────────────────────┤
│ + getInstance()     │
│ + getConfiguration()│
└─────────────────────┘

┌──────────────────────────┐
│ FinancialReportBuilder   │◄────── Builder
├──────────────────────────┤
│ - title                  │
│ - reportType             │
│ - country                │
├──────────────────────────┤
│ + setTitle()             │
│ + setCountry()           │
│ + build(): Report        │
└──────────────────────────┘

┌──────────────────────┐
│ ReportFactory        │◄────── Factory
├──────────────────────┤
│ + createReport()     │
│ + createSimpleReport()│
└──────────────────────┘

┌──────────────────────┐
│ ReportComponent      │◄────── Composite
│ (abstract)           │
├──────────────────────┤
│ # name               │
│ # level              │
├──────────────────────┤
│ + render()           │
│ + add()              │
│ + remove()           │
└──────────────────────┘
         △
         │
    ┌────┴────┐
    │         │
ReportSection ReportContent

┌──────────────────────┐
│ Report               │◄────── Decorator
│ (interface)          │
├──────────────────────┤
│ + generate()         │
└──────────────────────┘
         △
         │
    ┌────┴─────┐
    │          │
BasicReport  ReportDecorator
              │
      ┌───────┴───────┐
      │               │
HeaderDecorator  FooterDecorator
```

---

## 👥 Equipo de Desarrollo

- **Desarrollador Principal:** Omar Cordova Pintado 
- **Versión:** 1.0.0
- **Fecha:** 06 Noviembre 2024
- **Empresa:** FinanCorp S.A.

---

## 📝 Notas Adicionales

### Configuración de Producción
Para usar en producción, considera:
1. Cambiar a una base de datos persistente (PostgreSQL, MySQL)
2. Configurar seguridad (Spring Security)
3. Implementar autenticación y autorización
4. Añadir logs robustos
5. Configurar CORS según necesidades

### Escalabilidad
El sistema está diseñado para:
- ✅ Añadir nuevas filiales fácilmente
- ✅ Extender tipos de reportes
- ✅ Integrar nuevos decoradores
- ✅ Adaptar a nuevos formatos

---

## 📞 Soporte

Para preguntas o problemas:
- 📧 Email: soporte@financorp.com
- 📱 Teléfono: +34 XXX XXX XXX
- 🌐 Web: www.financorp.com

---

## 📄 Licencia

Este proyecto es propiedad de **FinanCorp S.A.** - Todos los derechos reservados © 2024

---

**🎉 ¡Gracias por usar SERF!**
