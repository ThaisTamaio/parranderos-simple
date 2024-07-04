package net.javaguides.banking.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import net.javaguides.banking.modelo.Frecuentan;
import net.javaguides.banking.modelo.FrecuentanPK;
import net.javaguides.banking.repositorio.BarRepository;
import net.javaguides.banking.repositorio.BebedorRepository;
import net.javaguides.banking.repositorio.FrecuentanRepository;

@RestController
@RequestMapping("/api/frecuentan")
public class FrecuentanController {

    @Autowired
    private BebedorRepository bebedorRepository;

    @Autowired
    private BarRepository barRepository;

    @Autowired
    private FrecuentanRepository frecuentanRepository;

    @GetMapping
    public List<Frecuentan> getAllFrecuentan() {
        return (List<Frecuentan>) frecuentanRepository.darFrecuentan();
    }

    @GetMapping("/by-date")
    public List<Frecuentan> getFrecuentanByDate(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return frecuentanRepository.findByFechaVisita(Date.valueOf(fecha));
    }

    @PostMapping
    public Frecuentan createFrecuentan(@RequestBody Frecuentan frecuentan) {
        FrecuentanPK pk = frecuentan.getPk();
        Integer idBebedor = pk.getBebedor().getId();
        Integer idBar = pk.getBar().getId();
        Date fechaVisita = pk.getFecha_visita();
        String horario = pk.getHorario();

        System.out.println("Fecha recibida: " + fechaVisita.toLocalDate());

        if (!bebedorRepository.existsById(idBebedor)) {
            throw new RuntimeException("Bebedor no encontrado");
        }

        if (!barRepository.existsById(idBar)) {
            throw new RuntimeException("Bar no encontrado");
        }

        frecuentanRepository.insertarFrecuentan(idBebedor, idBar, fechaVisita, horario);

        Frecuentan frecuentanCreado = frecuentanRepository.darFrecuentanPorId(idBebedor, idBar, fechaVisita, horario);

        System.out.println("Fecha almacenada: " + frecuentanCreado.getPk().getFecha_visita().toLocalDate());

        return frecuentanCreado;
    }

    @GetMapping("/{id_bebedor}/{id_bar}/{fecha_visita}/{horario}")
    public Frecuentan getFrecuentan(@PathVariable Integer id_bebedor, @PathVariable Integer id_bar, 
                                    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_visita, 
                                    @PathVariable String horario) {
        Date fechaVisitaSql = Date.valueOf(fecha_visita);
        Frecuentan frecuentan = frecuentanRepository.darFrecuentanPorId(id_bebedor, id_bar, fechaVisitaSql, horario);
        if (frecuentan == null) {
            throw new RuntimeException("Registro frecuentan no encontrado");
        }
        return frecuentan;
    }

    @DeleteMapping("/{id_bebedor}/{id_bar}/{fecha_visita}/{horario}")
    public void deleteFrecuentan(@PathVariable Integer id_bebedor, @PathVariable Integer id_bar, 
                                 @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_visita, 
                                 @PathVariable String horario) {
        Date fechaVisitaSql = Date.valueOf(fecha_visita);
        frecuentanRepository.eliminarFrecuentan(id_bebedor, id_bar, fechaVisitaSql, horario);
    }
}