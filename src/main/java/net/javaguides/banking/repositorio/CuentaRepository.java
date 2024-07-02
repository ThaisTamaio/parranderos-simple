package net.javaguides.banking.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import net.javaguides.banking.modelo.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cuenta (monto, cliente_id) VALUES (:monto, :clienteId)", nativeQuery = true)
    void insertarCuenta(@Param("monto") Double monto, @Param("clienteId") Long clienteId);

    @Query(value = "SELECT * FROM cuenta WHERE id = :id", nativeQuery = true)
    Optional<Cuenta> encontrarCuentaPorId(@Param("id") Long id);

    @Query(value = "SELECT * FROM cuenta WHERE cliente_id = :clienteId AND monto = :monto ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Cuenta> encontrarCuentaRecienCreada(@Param("clienteId") Long clienteId, @Param("monto") Double monto);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cuenta SET monto = :monto, cliente_id = :clienteId WHERE id = :id", nativeQuery = true)
    void actualizarCuenta(@Param("id") Long id, @Param("monto") Double monto, @Param("clienteId") Long clienteId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cuenta WHERE id = :id", nativeQuery = true)
    void eliminarCuenta(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cuenta SET monto = monto + :monto WHERE id = :id", nativeQuery = true)
    void aumentarMontoCuenta(@Param("id") Long id, @Param("monto") Double monto);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cuenta SET monto = monto - :monto WHERE id = :id", nativeQuery = true)
    void disminuirMontoCuenta(@Param("id") Long id, @Param("monto") Double monto);
}