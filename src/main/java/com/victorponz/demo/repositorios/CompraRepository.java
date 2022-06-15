package com.victorponz.demo.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorponz.demo.modelo.Compra;
import com.victorponz.demo.modelo.Usuario;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByPropietario(Usuario propietario);
    
    
}
