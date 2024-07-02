package net.javaguides.banking.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import net.javaguides.banking.modelo.Transaccion;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO transaccion (tipo, monto, fecha, cuenta_id) VALUES (:tipo, :monto, :fecha, :cuentaId)", nativeQuery = true)
    void insertarTransaccion(@Param("tipo") String tipo, @Param("monto") Double monto, @Param("fecha") LocalDateTime fecha, @Param("cuentaId") Long cuentaId);

    @Query(value = "SELECT * FROM transaccion WHERE cuenta_id = :cuentaId", nativeQuery = true)
    Collection<Transaccion> encontrarTransaccionesPorCuentaId(@Param("cuentaId") Long cuentaId);

    @Query(value = "SELECT * FROM transaccion WHERE cuenta_id = :cuentaId AND tipo = :tipo AND monto = :monto ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Transaccion> encontrarTransaccionRecienCreada(@Param("cuentaId") Long cuentaId, @Param("tipo") String tipo, @Param("monto") Double monto);
}