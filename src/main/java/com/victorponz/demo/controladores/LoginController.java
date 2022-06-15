package com.victorponz.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.victorponz.demo.modelo.Usuario;
import com.victorponz.demo.servicios.UsuarioServicio;
import com.victorponz.demo.upload.StorageService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


@Controller
public class LoginController {
    
    @Autowired
    UsuarioServicio usuarioServicio;


    @Autowired
    StorageService storageService;


    @GetMapping("/")
    public String welcome(){
        return ("redirect:public");
    }

    @GetMapping("/auth/login")
    public String login(Model modelo){
        modelo.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/auth/register")
    public String registro(@ModelAttribute Usuario usuario, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()){
            String imagen = storageService.store(file);
            usuario.setAvatar(MvcUriComponentsBuilder
					.fromMethodName(FilesController.class, "serveFile", imagen).build().toUriString());
        }

        usuarioServicio.registrar(usuario);
        return "/auth/login";
    }
    
}
