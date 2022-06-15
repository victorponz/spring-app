package com.victorponz.demo.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorponz.demo.modelo.Compra;
import com.victorponz.demo.modelo.Producto;
import com.victorponz.demo.modelo.Usuario;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    List<Producto> findByPropietario(Usuario propietario);
    List<Producto> findByCompra (Compra compra);
    List<Producto> findByCompraIsNull();
    List<Producto> findByNombreContainsIgnoreCaseAndCompraIsNull(String nombre);
    List<Producto> findByNombreContainsIgnoreCaseAndPropietario(String nombre, Usuario propietario);
}
