package com.victorponz.demo.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.victorponz.demo.modelo.Producto;
import com.victorponz.demo.modelo.Usuario;
import com.victorponz.demo.servicios.CompraServicio;
import com.victorponz.demo.servicios.ProductoServicio;
import com.victorponz.demo.servicios.UsuarioServicio;
import com.victorponz.demo.upload.StorageService;

@Controller
@RequestMapping("/app")
public class ProductoController {
    @Autowired
    CompraServicio compraServicio;

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

	@Autowired
	StorageService storageService;
  
    @ModelAttribute("misproductos")
    public List<Producto> misProductos(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u = usuarioServicio.buscarPorEmail(email);
        return productoServicio.productosDeUnPropietario(u);
    }

    @GetMapping({"/misproductos"})
    public String index(Model model, @RequestParam(name="q", required = false) String query){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u = usuarioServicio.buscarPorEmail(email);
 
        if (query != null){
            model.addAttribute("misproductos", productoServicio.buscarMisProductos(query, u));
        }

        return "app/producto/lista";
    }

    @GetMapping("/misproductos/{id}/eliminar")
    public String eliminar(@PathVariable Long id){
        Producto p = productoServicio.findById(id);
        if (p.getPropietario().getEmail() != SecurityContextHolder.getContext().getAuthentication().getName())
            return "redirect:/public";

        if (p.getCompra() == null)
            productoServicio.borrar(p);

        return "redirect:/app/misproductos";
    }

    @GetMapping("/producto/nuevo")
    public String nuevoProducto(Model model){
        model.addAttribute("producto", new Producto());
        return "app/producto/form";

    }

    @PostMapping("producto/nuevo/submit")
    public String nuevoProductoSubmit(@ModelAttribute Producto producto, @RequestParam("file") MultipartFile file){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u = usuarioServicio.buscarPorEmail(email);
        if (!file.isEmpty()){
            String imagen = storageService.store(file);
            producto.setImagen(MvcUriComponentsBuilder
					.fromMethodName(FilesController.class, "serveFile", imagen).build().toUriString());
        }
        producto.setPropietario(u);
        productoServicio.insertar(producto);
        return "redirect:/app/misproductos";
    }

}
