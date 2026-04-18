package com.example.inventarioproductosapi.repository;

import com.example.inventarioproductosapi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
