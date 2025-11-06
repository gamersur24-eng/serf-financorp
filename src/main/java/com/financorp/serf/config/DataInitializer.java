package com.financorp.serf.config;

import com.financorp.serf.model.Filial;
import com.financorp.serf.model.Producto;
import com.financorp.serf.model.Venta;
import com.financorp.serf.repository.FilialRepository;
import com.financorp.serf.repository.ProductoRepository;
import com.financorp.serf.repository.VentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Inicializa datos de prueba en la base de datos
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private FilialRepository filialRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private VentaRepository ventaRepository;
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("Inicializando datos de prueba...");
        
        // Crear filiales
        Filial filialEspana = createFilial("ES-001", "Filial Madrid", "España", "Madrid", "EUR", "Carlos García");
        Filial filialMexico = createFilial("MX-001", "Filial Ciudad de México", "México", "Ciudad de México", "MXN", "María López");
        Filial filialArgentina = createFilial("AR-001", "Filial Buenos Aires", "Argentina", "Buenos Aires", "ARS", "Juan Rodríguez");
        Filial filialPeru = createFilial("PE-001", "Filial Lima", "Perú", "Lima", "PEN", "Ana Torres");
        
        // Crear productos para España
        Producto laptop1 = createProducto("LAP-001", "Laptop Dell XPS 15", Producto.CategoriaProducto.LAPTOP,
                "Dell", "XPS 15", new BigDecimal("800"), new BigDecimal("1200"), 50, filialEspana);
        Producto phone1 = createProducto("PHN-001", "Smartphone Samsung Galaxy S23", Producto.CategoriaProducto.SMARTPHONE,
                "Samsung", "Galaxy S23", new BigDecimal("600"), new BigDecimal("900"), 100, filialEspana);
        
        // Crear productos para México
        Producto laptop2 = createProducto("LAP-002", "Laptop Lenovo ThinkPad", Producto.CategoriaProducto.LAPTOP,
                "Lenovo", "ThinkPad X1", new BigDecimal("750"), new BigDecimal("1150"), 30, filialMexico);
        Producto tablet1 = createProducto("TAB-001", "Tablet Huawei MatePad", Producto.CategoriaProducto.TABLET,
                "Huawei", "MatePad Pro", new BigDecimal("300"), new BigDecimal("450"), 75, filialMexico);
        
        // Crear productos para Argentina
        Producto monitor1 = createProducto("MON-001", "Monitor LG UltraWide", Producto.CategoriaProducto.MONITOR,
                "LG", "34WN80C", new BigDecimal("400"), new BigDecimal("600"), 40, filialArgentina);
        
        // Crear productos para Perú
        Producto router1 = createProducto("NET-001", "Router TP-Link AX6000", Producto.CategoriaProducto.EQUIPO_RED,
                "TP-Link", "AX6000", new BigDecimal("150"), new BigDecimal("250"), 60, filialPeru);
        
        // Crear ventas de ejemplo
        createVenta("ES-001-V-00001", laptop1, filialEspana, 5, new BigDecimal("1200"), "Juan Pérez", "12345678A");
        createVenta("ES-001-V-00002", phone1, filialEspana, 10, new BigDecimal("900"), "María García", "87654321B");
        createVenta("MX-001-V-00001", laptop2, filialMexico, 3, new BigDecimal("1150"), "Pedro Sánchez", "RFC123456");
        createVenta("AR-001-V-00001", monitor1, filialArgentina, 8, new BigDecimal("600"), "Laura Martínez", "DNI789012");
        createVenta("PE-001-V-00001", router1, filialPeru, 15, new BigDecimal("250"), "Carlos Ramírez", "DNI345678");
        
        logger.info("✅ Datos de prueba inicializados correctamente");
        logger.info("📊 Total Filiales: " + filialRepository.count());
        logger.info("📦 Total Productos: " + productoRepository.count());
        logger.info("💰 Total Ventas: " + ventaRepository.count());
    }
    
    private Filial createFilial(String codigo, String nombre, String pais, String ciudad, String moneda, String responsable) {
        Filial filial = new Filial();
        filial.setCodigo(codigo);
        filial.setNombre(nombre);
        filial.setPais(pais);
        filial.setCiudad(ciudad);
        filial.setMoneda(moneda);
        filial.setResponsable(responsable);
        filial.setActiva(true);
        return filialRepository.save(filial);
    }
    
    private Producto createProducto(String sku, String nombre, Producto.CategoriaProducto categoria,
                                   String marca, String modelo, BigDecimal precioCompra, 
                                   BigDecimal precioVenta, Integer stock, Filial filial) {
        Producto producto = new Producto();
        producto.setSku(sku);
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        producto.setModelo(modelo);
        producto.setPrecioCompra(precioCompra);
        producto.setPrecioVenta(precioVenta);
        producto.setStockActual(stock);
        producto.setStockMinimo(10);
        producto.setFilial(filial);
        producto.setDescripcion("Producto tecnológico importado de China de alta calidad");
        return productoRepository.save(producto);
    }
    
    private Venta createVenta(String codigoVenta, Producto producto, Filial filial, 
                            Integer cantidad, BigDecimal precioUnitario, 
                            String clienteNombre, String clienteDocumento) {
        Venta venta = new Venta();
        venta.setCodigoVenta(codigoVenta);
        venta.setProducto(producto);
        venta.setFilial(filial);
        venta.setCantidad(cantidad);
        venta.setPrecioUnitario(precioUnitario);
        venta.setImpuesto(new BigDecimal("21")); // 21% IVA
        venta.setClienteNombre(clienteNombre);
        venta.setClienteDocumento(clienteDocumento);
        venta.setEstado(Venta.EstadoVenta.COMPLETADA);
        venta.setFormaPago("Tarjeta de Crédito");
        venta.setFechaVenta(LocalDateTime.now().minusDays((long) (Math.random() * 30)));
        
        // Actualizar stock
        producto.setStockActual(producto.getStockActual() - cantidad);
        productoRepository.save(producto);
        
        return ventaRepository.save(venta);
    }
}
