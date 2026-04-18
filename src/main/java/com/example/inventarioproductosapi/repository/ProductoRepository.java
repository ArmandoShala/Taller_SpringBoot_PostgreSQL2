package com.example.inventarioproductosapi.repository;

import com.example.inventarioproductosapi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaIgnoreCase(String categoria);

    Optional<Producto> findTopByOrderByPrecioDesc();

    long countByStockGreaterThan(Integer stock);

    boolean existsBySku(String sku);
}
