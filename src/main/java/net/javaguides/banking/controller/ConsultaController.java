package net.javaguides.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.javaguides.banking.repositorio.ConsultaRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping("/cuentas/saldoMayorA")
    public List<Map<String, Object>> getCuentasConSaldoMayorA(@RequestParam double monto) {
        return consultaRepository.findCuentasConSaldoMayorA(monto);
    }

    @GetMapping("/clientes/masDeUnaCuenta")
    public List<Map<String, Object>> getClientesConMasDeUnaCuenta() {
        return consultaRepository.findClientesConMasDeUnaCuenta();
    }

    @GetMapping("/transacciones/rangoFechas")
    public List<Map<String, Object>> getTransaccionesEnRangoDeFechas(@RequestParam String startDate, @RequestParam String endDate) {
        return consultaRepository.findTransaccionesEnRangoDeFechas(startDate, endDate);
    }

    @GetMapping("/clientes/{clienteId}/historialTransacciones")
    public List<Map<String, Object>> getHistorialTransaccionesParaCliente(@PathVariable long clienteId) {
        return consultaRepository.findHistorialTransaccionesParaCliente(clienteId);
    }

}
