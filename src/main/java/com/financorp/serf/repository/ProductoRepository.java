package com.financorp.serf.repository;

import com.financorp.serf.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findBySku(String sku);
    List<Producto> findByFilialId(Long filialId);
    List<Producto> findByCategoria(Producto.CategoriaProducto categoria);
    
    @Query("SELECT p FROM Producto p WHERE p.stockActual < p.stockMinimo")
    List<Producto> findByStockActualLessThanStockMinimo();
    
    @Query("SELECT p FROM Producto p WHERE p.filial.pais = :pais")
    List<Producto> findByPais(String pais);
}
