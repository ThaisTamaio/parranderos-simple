package net.javaguides.banking.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import net.javaguides.banking.modelo.Bebida;
import net.javaguides.banking.modelo.TipoBebida;
import net.javaguides.banking.repositorio.BebidaRepository;
import net.javaguides.banking.repositorio.TipoBebidaRepository;

@RestController
@RequestMapping("/api/bebidas")
public class BebidasController {

    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private TipoBebidaRepository tipoBebidaRepository;

    @GetMapping
    public Collection<Bebida> getBebidas(@RequestParam(required = false) String ciudad, @RequestParam(required = false) String minGrado, @RequestParam(required = false) String maxGrado) {
        if ((ciudad == null || ciudad.isEmpty()) || (minGrado == null || minGrado.isEmpty()) || (maxGrado == null || maxGrado.isEmpty())) {
            return bebidaRepository.darBebidas();
        } else {
            return bebidaRepository.darBebidasPorCiudadYGrado(ciudad, Integer.parseInt(minGrado), Integer.parseInt(maxGrado));
        }
    }

    @PostMapping
    public Bebida createBebida(@RequestBody Bebida bebida) {
        TipoBebida tipoBebida = tipoBebidaRepository.findById(bebida.getTipo().getId()).orElse(null);
        if (tipoBebida != null) {
            bebida.setTipo(tipoBebida);
            return bebidaRepository.save(bebida); // Guardar y devolver la bebida creada
        } else {
            throw new IllegalArgumentException("Tipo de bebida no encontrado");
        }
    }

    @GetMapping("/{id}")
    public Bebida getBebida(@PathVariable long id) {
        return bebidaRepository.findById((int) id).orElse(null); // Utiliza findById del repositorio JPA
    }

    @PutMapping("/{id}")
    public Bebida updateBebida(@PathVariable long id, @RequestBody Bebida bebida) {
        TipoBebida tipoBebida = tipoBebidaRepository.findById(bebida.getTipo().getId()).orElse(null);
        if (tipoBebida != null) {
            bebida.setId((int) id); // Asegura que el ID está configurado en el objeto Bebida
            bebida.setTipo(tipoBebida);
            return bebidaRepository.save(bebida); // Actualizar y devolver la bebida actualizada
        } else {
            throw new IllegalArgumentException("Tipo de bebida no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBebida(@PathVariable long id) {
        bebidaRepository.deleteById((int) id); // Elimina la bebida por ID
    }


}