package net.javaguides.banking.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import net.javaguides.banking.modelo.Bar;
import net.javaguides.banking.repositorio.BarRepository;
import net.javaguides.banking.servicios.BaresServicio;

@RestController
@RequestMapping("/api/bares")
public class BaresController {

    @Autowired
    private BarRepository barRepository;

    @Autowired
    private BaresServicio baresServicio;

    @GetMapping
    public Collection<Bar> getBares(@RequestParam(required = false) String ciudad, @RequestParam(required = false) String tipo) throws InterruptedException {
        if ((ciudad == null || ciudad.isEmpty()) && (tipo == null || tipo.isEmpty())) {
            return baresServicio.consultarBaresConFantasma();
        } else {
            return barRepository.darBaresPorCiudadYTipoBebida(ciudad, tipo);
        }
    }

    @PostMapping
    public Bar createBar(@RequestBody Bar bar) {
        return barRepository.save(bar); // Guardar y devolver el bar creado
    }

    @GetMapping("/{id}")
    public Bar getBar(@PathVariable long id) {
        return barRepository.findById((int) id).orElse(null); // Utiliza findById del repositorio JPA
    }

    @PutMapping("/{id}")
    public Bar updateBar(@PathVariable long id, @RequestBody Bar bar) {
        bar.setId((int) id); // Asegura que el ID está configurado en el objeto Bar
        return barRepository.save(bar); // Actualizar y devolver el bar actualizado
    }

    @DeleteMapping("/{id}")
    public void deleteBar(@PathVariable long id) {
        barRepository.deleteById((int) id); // Elimina el bar por ID
    }

    @PostMapping("/actualizar_bar")
    public void transferir_1() {
        Collection<Bar> bares = barRepository.darBares();
        if (!bares.isEmpty()) {
            Bar primerBar = bares.iterator().next();
            baresServicio.actualizar_bar_con_espera(primerBar.getId(), "Bogota");
            System.out.println("El bar transferido es: " + primerBar.getNombre());
            System.out.println("Su ID es: " + primerBar.getId());
        }
    }

    @PostMapping("/actualizar_bar_inmediato")
    public void transferir_2() {
        Collection<Bar> bares = barRepository.darBares();
        if (!bares.isEmpty()) {
            try {
                Bar primerBar = bares.iterator().next();
                baresServicio.actualizar_bar_sin_espera(primerBar.getId(), "Bogota");
                System.out.println("El bar transferido es: " + primerBar.getNombre());
                System.out.println("Su ID es: " + primerBar.getId());
            } catch (Exception e) {
                System.err.println("Error al transferir el bar: " + e.getMessage());
            }
        }
    }

    @PostMapping("/bloqueo")
    public void consultarBaresSinFantasma() {
        try {
            baresServicio.consultarBaresSinFantasma();
        } catch (Exception e) {
            System.err.println("Error durante la consulta de bares: " + e.getMessage());
        }
    }


}