package net.javaguides.banking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import net.javaguides.banking.modelo.Cuenta;
import net.javaguides.banking.modelo.Transaccion;
import net.javaguides.banking.repositorio.CuentaRepository;
import net.javaguides.banking.repositorio.TransaccionRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @PostMapping
    public Transaccion crearTransaccion(@RequestBody Transaccion transaccion) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.encontrarCuentaPorId(transaccion.getCuenta().getId());

        if (cuentaOpt.isPresent()) {
            Cuenta cuenta = cuentaOpt.get();
            if ("DEPOSITO".equalsIgnoreCase(transaccion.getTipo())) {
                cuentaRepository.aumentarMontoCuenta(cuenta.getId(), transaccion.getMonto());
            } else if ("RETIRO".equalsIgnoreCase(transaccion.getTipo())) {
                if (cuenta.getMonto() >= transaccion.getMonto()) {
                    cuentaRepository.disminuirMontoCuenta(cuenta.getId(), transaccion.getMonto());
                } else {
                    throw new RuntimeException("Saldo insuficiente para realizar el retiro");
                }
            } else {
                throw new RuntimeException("Tipo de transacción no soportado");
            }

            transaccionRepository.insertarTransaccion(
                transaccion.getTipo(),
                transaccion.getMonto(),
                LocalDateTime.now(),
                cuenta.getId()
            );

            // Obtener la transacción recién creada para devolverla
            Optional<Transaccion> transaccionCreada = transaccionRepository.encontrarTransaccionRecienCreada(
                cuenta.getId(), transaccion.getTipo(), transaccion.getMonto());

            return transaccionCreada.orElseThrow(() -> new RuntimeException("Error al crear la transacción"));
        } else {
            throw new RuntimeException("Cuenta no encontrada");
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public Transaccion actualizarTransaccion(@PathVariable Long id, @RequestBody Transaccion transaccion) throws Exception {
        Transaccion transaccionExistente = transaccionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        // Si la cuenta no está presente en la solicitud, usa la cuenta de la transacción existente
        if (transaccion.getCuenta() == null || transaccion.getCuenta().getId() == null) {
            transaccion.setCuenta(transaccionExistente.getCuenta());
        }

        Optional<Cuenta> cuentaOpt = cuentaRepository.encontrarCuentaPorId(transaccion.getCuenta().getId());

        if (cuentaOpt.isPresent()) {
            Cuenta cuenta = cuentaOpt.get();
            // Revertir el efecto de la transacción existente
            if ("DEPOSITO".equalsIgnoreCase(transaccionExistente.getTipo())) {
                cuenta.setMonto(cuenta.getMonto() - transaccionExistente.getMonto());
            } else if ("RETIRO".equalsIgnoreCase(transaccionExistente.getTipo())) {
                cuenta.setMonto(cuenta.getMonto() + transaccionExistente.getMonto());
            }

            // Obtener el saldo actual después de revertir la transacción
            Double saldoActual = cuenta.getMonto();

            // Verificar si la nueva transacción causará saldo negativo
            if ("RETIRO".equalsIgnoreCase(transaccion.getTipo()) && saldoActual < transaccion.getMonto()) {
                // Revertir los cambios realizados por la reversión de la transacción existente
                if ("DEPOSITO".equalsIgnoreCase(transaccionExistente.getTipo())) {
                    cuenta.setMonto(cuenta.getMonto() + transaccionExistente.getMonto());
                } else if ("RETIRO".equalsIgnoreCase(transaccionExistente.getTipo())) {
                    cuenta.setMonto(cuenta.getMonto() - transaccionExistente.getMonto());
                }
                throw new Exception("Saldo insuficiente para realizar el retiro");
            }

            // Aplicar el efecto de la nueva transacción
            if ("DEPOSITO".equalsIgnoreCase(transaccion.getTipo())) {
                cuenta.setMonto(cuenta.getMonto() + transaccion.getMonto());
            } else if ("RETIRO".equalsIgnoreCase(transaccion.getTipo())) {
                cuenta.setMonto(cuenta.getMonto() - transaccion.getMonto());
            } else {
                throw new RuntimeException("Tipo de transacción no soportado");
            }

            transaccionExistente.setTipo(transaccion.getTipo());
            transaccionExistente.setMonto(transaccion.getMonto());
            transaccionExistente.setFecha(LocalDateTime.now());

            cuentaRepository.save(cuenta);
            transaccionRepository.save(transaccionExistente);

            return transaccionExistente;
        } else {
            throw new RuntimeException("Cuenta no encontrada");
        }
    }

    @GetMapping("/cuenta/{cuentaId}")
    public Collection<Transaccion> obtenerTransaccionesPorCuenta(@PathVariable Long cuentaId) {
        return transaccionRepository.encontrarTransaccionesPorCuentaId(cuentaId);
    }
}