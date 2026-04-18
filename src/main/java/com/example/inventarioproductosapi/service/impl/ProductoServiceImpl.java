package com.example.inventarioproductosapi.service.impl;

import com.example.inventarioproductosapi.exception.ResourceNotFoundException;
import com.example.inventarioproductosapi.model.Producto;
import com.example.inventarioproductosapi.repository.ProductoRepository;
import com.example.inventarioproductosapi.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    @Override
    public Producto crear(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Long id, Producto producto) {
        Producto productoExistente = obtenerPorId(id);

        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setStock(producto.getStock());

        return productoRepository.save(productoExistente);
    }

    @Override
    public void eliminar(Long id) {
        Producto productoExistente = obtenerPorId(id);
        productoRepository.delete(productoExistente);
    }
}
