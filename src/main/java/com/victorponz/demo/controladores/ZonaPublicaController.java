package com.victorponz.demo.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.victorponz.demo.modelo.Producto;
import com.victorponz.demo.servicios.ProductoServicio;

@Controller
@RequestMapping("/public")
public class ZonaPublicaController {
    
    @Autowired
    ProductoServicio productoServicio;

    @ModelAttribute("productos")
    public List<Producto> productosNoVendidos(){
        return productoServicio.productosSinVender();
    }

    @GetMapping({"/", "/index"})
    public String index(Model model, @RequestParam(name="q", required = false) String query){
        if (query != null){
            model.addAttribute("productos", productoServicio.buscar(query));
        }

        return "index";
    }

    @GetMapping("/producto/{id}")
    public String showProduct(Model modelo, @PathVariable Long id){
        Producto p = productoServicio.findById(id);
        if (p != null)
            modelo.addAttribute("producto", p);
        
        return "redirect:/public";
    }
    
}
