package com.example.inventarioproductosapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Getter
@Setter
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

    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false)
    private String categoria;

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    @Builder.Default
    @Column(nullable = false)
    private Integer stock = 0;

    @NotBlank(message = "El SKU es obligatorio")
    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaIngreso;

    private String fabricante;

    @PrePersist
    public void prePersist() {
        if (fechaIngreso == null) {
            fechaIngreso = LocalDateTime.now();
        }
        if (stock == null) {
            stock = 0;
        }
    }
}
