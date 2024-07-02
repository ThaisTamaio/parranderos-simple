package net.javaguides.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import net.javaguides.banking.modelo.Cuenta;
import net.javaguides.banking.repositorio.ClienteRepository;
import net.javaguides.banking.repositorio.CuentaRepository;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public Cuenta crearCuenta(@RequestBody Cuenta cuenta) {
        if (clienteRepository.existsById(cuenta.getCliente().getId())) {
            cuentaRepository.insertarCuenta(cuenta.getMonto(), cuenta.getCliente().getId());

            // Obtener la cuenta recién creada para devolverla
            Optional<Cuenta> cuentaCreada = cuentaRepository.encontrarCuentaRecienCreada(cuenta.getCliente().getId(), cuenta.getMonto());

            return cuentaCreada.orElseThrow(() -> new RuntimeException("Error al crear la cuenta"));
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
    }

    @GetMapping("/{id}")
    public Cuenta obtenerCuenta(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaRepository.encontrarCuentaPorId(id);
        return cuenta.orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @PutMapping("/{id}")
    public Cuenta actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        if (cuentaRepository.existsById(id) && clienteRepository.existsById(cuenta.getCliente().getId())) {
            cuentaRepository.actualizarCuenta(id, cuenta.getMonto(), cuenta.getCliente().getId());

            Optional<Cuenta> cuentaActualizada = cuentaRepository.encontrarCuentaPorId(id);

            return cuentaActualizada.orElseThrow(() -> new RuntimeException("Error al actualizar la cuenta"));
        } else {
            throw new RuntimeException("Cuenta o cliente no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public String eliminarCuenta(@PathVariable Long id) {
        if (cuentaRepository.existsById(id)) {
            cuentaRepository.eliminarCuenta(id);
            return "Cuenta eliminada exitosamente";
        } else {
            throw new RuntimeException("Cuenta no encontrada");
        }
    }
}