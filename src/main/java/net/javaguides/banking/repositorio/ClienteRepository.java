package net.javaguides.banking.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import net.javaguides.banking.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cliente (nombre) VALUES (:nombre)", nativeQuery = true)
    void insertarCliente(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM cliente WHERE id = :id", nativeQuery = true)
    Optional<Cliente> encontrarClientePorId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cliente SET nombre = :nombre WHERE id = :id", nativeQuery = true)
    void actualizarCliente(@Param("id") Long id, @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cliente WHERE id = :id", nativeQuery = true)
    void eliminarCliente(@Param("id") Long id);

    @Query(value = "SELECT * FROM cliente WHERE nombre = :nombre ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Cliente> encontrarClienteRecienCreado(@Param("nombre") String nombre);
}