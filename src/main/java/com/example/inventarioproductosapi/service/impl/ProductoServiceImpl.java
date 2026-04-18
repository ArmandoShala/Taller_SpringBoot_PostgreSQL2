package com.example.inventarioproductosapi.service.impl;

import com.example.inventarioproductosapi.exception.DuplicateResourceException;
import com.example.inventarioproductosapi.exception.ResourceNotFoundException;
import com.example.inventarioproductosapi.model.Producto;
import com.example.inventarioproductosapi.repository.ProductoRepository;
import com.example.inventarioproductosapi.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

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
        validarSkuUnicoParaCreacion(producto.getSku());
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Long id, Producto producto) {
        Producto productoExistente = obtenerPorId(id);

        validarSkuUnicoEnActualizacion(productoExistente, producto.getSku());

        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setCategoria(producto.getCategoria());
        productoExistente.setStock(producto.getStock());
        productoExistente.setSku(producto.getSku());
        productoExistente.setFabricante(producto.getFabricante());
        // No se actualiza id ni fechaIngreso para preservar datos originales.

        return productoRepository.save(productoExistente);
    }

    @Override
    public void eliminar(Long id) {
        Producto productoExistente = obtenerPorId(id);
        productoRepository.delete(productoExistente);
    }

    @Override
    public Producto obtenerProductoConMayorPrecio() {
        return productoRepository.findTopByOrderByPrecioDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No hay productos registrados."));
    }

    @Override
    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoriaIgnoreCase(categoria);
    }

    @Override
    public long contarProductosEnStock() {
        return productoRepository.countByStockGreaterThan(0);
    }

    private void validarSkuUnicoParaCreacion(String sku) {
        if (productoRepository.existsBySku(sku)) {
            throw new DuplicateResourceException("Ya existe un producto con el SKU: " + sku);
        }
    }

    private void validarSkuUnicoEnActualizacion(Producto productoExistente, String nuevoSku) {
        boolean cambioSku = !Objects.equals(productoExistente.getSku(), nuevoSku);
        if (cambioSku && productoRepository.existsBySku(nuevoSku)) {
            throw new DuplicateResourceException("Ya existe otro producto con el SKU: " + nuevoSku);
        }
    }
}
