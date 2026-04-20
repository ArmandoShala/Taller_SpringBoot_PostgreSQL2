package Producto.demo.persona.service;

import Producto.demo.persona.entity.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> listarTodos();

    Producto obtenerPorId(Long id);

    Producto crear(Producto producto);

    Producto actualizar(Long id, Producto producto);

    void eliminar(Long id);

    Producto obtenerProductoConMayorPrecio();

    List<Producto> obtenerPorCategoria(String categoria);

    long contarProductosEnStock();
}
