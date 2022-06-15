package com.victorponz.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorponz.demo.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Usuario findFirstByEmail(String email);
}
