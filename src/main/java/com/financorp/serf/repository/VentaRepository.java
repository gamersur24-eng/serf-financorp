package com.financorp.serf.repository;

import com.financorp.serf.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findByCodigoVenta(String codigoVenta);
    List<Venta> findByFilialId(Long filialId);
    List<Venta> findByEstado(Venta.EstadoVenta estado);
    List<Venta> findByFechaVentaBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT v FROM Venta v WHERE v.filial.pais = :pais AND v.fechaVenta BETWEEN :start AND :end")
    List<Venta> findByPaisAndFechaBetween(String pais, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.filial.id = :filialId AND v.estado = 'COMPLETADA'")
    Double getTotalVentasByFilial(Long filialId);
}
