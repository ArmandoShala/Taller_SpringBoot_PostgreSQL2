package com.example.inventarioproductosapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "productos") // Mapea esta entidad a la tabla productos en PostgreSQL.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio;

    @NotBlank(message = "La categoria es obligatoria")
    @Column(nullable = false)
    private String categoria;

    @Builder.Default
    private Integer stock = 0; // Valor por defecto cuando no se envía en la creación.

    @NotBlank(message = "El sku es obligatorio")
    @Column(unique = true, nullable = false) // Fuerza unicidad de SKU a nivel de BD.
    private String sku;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime fechaIngreso = LocalDateTime.now();

    private String fabricante;
}
