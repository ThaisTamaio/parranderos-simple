package net.javaguides.banking.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import net.javaguides.banking.modelo.Bebedor;
import net.javaguides.banking.repositorio.BebedorRepository;

@RestController
@RequestMapping("/api/bebedores")
public class BebedoresController {

    @Autowired
    private BebedorRepository bebedorRepository;

    @GetMapping
    public Collection<Bebedor> getBebedores(@RequestParam(required = false) String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            return bebedorRepository.darBebedoresPorNombre(nombre);
        } else {
            return bebedorRepository.darBebedores();
        }
    }

    @PostMapping
    public Bebedor createBebedor(@RequestBody Bebedor bebedor) {
        return bebedorRepository.save(bebedor); // Guardar y devolver el bebedor creado
    }

    @GetMapping("/{id}")
    public Bebedor getBebedor(@PathVariable long id) {
        return bebedorRepository.findById((int) id).orElse(null); // Utiliza findById del repositorio JPA
    }

    @PutMapping("/{id}")
    public Bebedor updateBebedor(@PathVariable long id, @RequestBody Bebedor bebedor) {
        bebedor.setId((int) id); // Asegura que el ID está configurado en el objeto Bebedor
        return bebedorRepository.save(bebedor); // Actualizar y devolver el bebedor actualizado
    }

    @DeleteMapping("/{id}")
    public void deleteBebedor(@PathVariable long id) {
        bebedorRepository.deleteById((int) id); // Elimina el bebedor por ID
    }

}
