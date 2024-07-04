package net.javaguides.banking.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import net.javaguides.banking.modelo.TipoBebida;
import net.javaguides.banking.repositorio.TipoBebidaRepository;

@RestController
@RequestMapping("/api/tipos-bebida")
public class TipoBebidaController {

    @Autowired
    private TipoBebidaRepository tipoBebidaRepository;

    @GetMapping
    public Collection<TipoBebida> getTiposBebida() {
        return tipoBebidaRepository.darTipos_bebida();
    }

    @GetMapping("/{id}")
    public TipoBebida getTipoBebida(@PathVariable long id) {
        return tipoBebidaRepository.findById((int) id).orElse(null);
    }

    @PostMapping
    public TipoBebida createTipoBebida(@RequestBody TipoBebida tipoBebida) {
        return tipoBebidaRepository.save(tipoBebida);
    }

    @PutMapping("/{id}")
    public TipoBebida updateTipoBebida(@PathVariable long id, @RequestBody TipoBebida tipoBebida) {
        tipoBebida.setId((int) id); // Asegura que el ID está configurado en el objeto TipoBebida
        return tipoBebidaRepository.save(tipoBebida);
    }

    @DeleteMapping("/{id}")
    public void deleteTipoBebida(@PathVariable long id) {
        tipoBebidaRepository.deleteById((int) id);
    }
}
