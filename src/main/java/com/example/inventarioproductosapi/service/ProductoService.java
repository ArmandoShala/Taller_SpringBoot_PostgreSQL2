package com.example.inventarioproductosapi.service;

import com.example.inventarioproductosapi.model.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> listarTodos();

    Producto obtenerPorId(Long id);

    Producto crear(Producto producto);

    Producto actualizar(Long id, Producto producto);

    void eliminar(Long id);
}
