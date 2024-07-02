package net.javaguides.banking.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import net.javaguides.banking.modelo.Cliente;

import java.util.List;
import java.util.Map;

@Repository
public interface ConsultaRepository extends CrudRepository<Cliente, Long> {

    @Query(value = "SELECT * FROM cuenta WHERE monto > :monto", nativeQuery = true)
    List<Map<String, Object>> findCuentasConSaldoMayorA(@Param("monto") double monto);
    
    @Query(value = "SELECT cliente.id, cliente.nombre, COUNT(cuenta.id) AS num_cuentas FROM cliente JOIN cuenta ON cliente.id = cuenta.cliente_id GROUP BY cliente.id HAVING num_cuentas > 1", nativeQuery = true)
    List<Map<String, Object>> findClientesConMasDeUnaCuenta();
    
    @Query(value = "SELECT * FROM transaccion WHERE fecha BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Map<String, Object>> findTransaccionesEnRangoDeFechas(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
    @Query(value = "SELECT cliente.id, cliente.nombre, SUM(cuenta.monto) AS saldo_total FROM cliente JOIN cuenta ON cliente.id = cuenta.cliente_id GROUP BY cliente.id", nativeQuery = true)
    List<Map<String, Object>> findSaldoTotalPorCliente();
    
    @Query(value = "SELECT transaccion.* FROM transaccion JOIN cuenta ON transaccion.cuenta_id = cuenta.id JOIN cliente ON cuenta.cliente_id = cliente.id WHERE cliente.id = :clienteId", nativeQuery = true)
    List<Map<String, Object>> findHistorialTransaccionesParaCliente(@Param("clienteId") long clienteId);
}