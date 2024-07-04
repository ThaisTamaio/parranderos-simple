package net.javaguides.banking.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.javaguides.banking.modelo.Sirven;
import net.javaguides.banking.modelo.SirvenPK;
import net.javaguides.banking.repositorio.BarRepository;
import net.javaguides.banking.repositorio.BebidaRepository;
import net.javaguides.banking.repositorio.SirvenRepository;
import net.javaguides.banking.servicios.SirvenServicio;

@RestController
@RequestMapping("/api/sirven")
public class SirvenController {

    @Autowired
    private SirvenRepository sirvenRepository;

    @Autowired
    private BarRepository barRepository;

    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private SirvenServicio sirvenServicio;

    @GetMapping
    public List<Sirven> getAllSirven() {
        return (List<Sirven>) sirvenRepository.darSirven();
    }

    @PostMapping
    public Sirven createSirven(@RequestBody Sirven sirven) {
        SirvenPK pk = sirven.getPk();
        Integer idBar = pk.getBar().getId();
        Integer idBebida = pk.getBebida().getId();
        String horario = pk.getHorario();

        if (!barRepository.existsById(idBar)) {
            throw new RuntimeException("Bar no encontrado: " + idBar);
        }

        if (!bebidaRepository.existsById(idBebida)) {
            throw new RuntimeException("Bebida no encontrada: " + idBebida);
        }

        sirvenRepository.insertarSirven(idBar, idBebida, horario);

        return sirvenRepository.darSirvenPorId(idBar, idBebida, horario);
    }

    @GetMapping("/{id_bar}/{id_bebida}/{horario}")
    public Sirven getSirven(@PathVariable Integer id_bar, @PathVariable Integer id_bebida,
                            @PathVariable String horario) {
        Sirven sirven = sirvenRepository.darSirvenPorId(id_bar, id_bebida, horario);
        if (sirven == null) {
            throw new RuntimeException("Registro sirven no encontrado");
        }
        return sirven;
    }

    @DeleteMapping("/{id_bar}/{id_bebida}/{horario}")
    public void deleteSirven(@PathVariable Integer id_bar, @PathVariable Integer id_bebida,
                             @PathVariable String horario) {
        sirvenRepository.eliminarSirven(id_bar, id_bebida, horario);
    }

    @PutMapping("/{id_bar}/{id_bebida}/{horario}")
    public Sirven updateSirven(@PathVariable Integer id_bar, @PathVariable Integer id_bebida,
                               @PathVariable String horario, @RequestBody Sirven sirvenActualizado) {
        SirvenPK pk = sirvenActualizado.getPk();
        Integer idBarActualizado = pk.getBar().getId();
        Integer idBebidaActualizada = pk.getBebida().getId();
        String horarioActualizado = pk.getHorario();

        if (!barRepository.existsById(idBarActualizado)) {
            throw new RuntimeException("Bar no encontrado: " + idBarActualizado);
        }

        if (!bebidaRepository.existsById(idBebidaActualizada)) {
            throw new RuntimeException("Bebida no encontrada: " + idBebidaActualizada);
        }

        // Eliminar el registro anterior
        sirvenRepository.eliminarSirven(id_bar, id_bebida, horario);

        // Insertar el nuevo registro con los valores actualizados
        sirvenRepository.insertarSirven(idBarActualizado, idBebidaActualizada, horarioActualizado);

        return sirvenRepository.darSirvenPorId(idBarActualizado, idBebidaActualizada, horarioActualizado);
    }

    @PostMapping("/transferir")
    public void transferirSirven(@RequestParam Integer barId, @RequestParam Integer bebidaId,
                                 @RequestParam String horario, @RequestParam Integer nuevoBarId) {
        sirvenServicio.transferir_bebida(barId, bebidaId, horario, nuevoBarId);
    }
}