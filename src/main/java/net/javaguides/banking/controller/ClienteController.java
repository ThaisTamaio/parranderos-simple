package net.javaguides.banking.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.javaguides.banking.modelo.Cliente;
import net.javaguides.banking.repositorio.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        clienteRepository.insertarCliente(cliente.getNombre());
        Optional<Cliente> clienteCreado = clienteRepository.encontrarClienteRecienCreado(cliente.getNombre());

        return clienteCreado.orElseThrow(() -> new RuntimeException("Error al crear el cliente"));
    }

    @GetMapping("/{id}")
    public Cliente obtenerCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.encontrarClientePorId(id);
        return cliente.orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.actualizarCliente(id, cliente.getNombre());

            // Obtener el cliente actualizado para devolverlo
            Optional<Cliente> clienteActualizado = clienteRepository.encontrarClientePorId(id);

            return clienteActualizado.orElseThrow(() -> new RuntimeException("Error al actualizar el cliente"));
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.eliminarCliente(id);
            return "Cliente eliminado exitosamente";
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
    }
}