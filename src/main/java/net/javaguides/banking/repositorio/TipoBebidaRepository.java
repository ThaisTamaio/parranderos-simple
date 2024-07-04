package net.javaguides.banking.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import net.javaguides.banking.modelo.TipoBebida;

public interface TipoBebidaRepository  extends JpaRepository<TipoBebida, Integer>{

    @Query(value = "SELECT * FROM tipos_bebida", nativeQuery = true)
    Collection<TipoBebida> darTipos_bebida();

    @Query(value = "SELECT * FROM tipos_bebida WHERE id = :id", nativeQuery = true)
    TipoBebida darTipoBebida(@Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tipos_bebida WHERE id = :id", nativeQuery = true)
    void eliminarTipoBebida(@Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tipos_bebida SET nombre = :nombre WHERE id = :id", nativeQuery = true)
    void actualizarTipoBebida(@Param("id") long id, @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tipos_bebida (id, nombre) VALUES ( parranderos_sequence.nextval , :nombre)", nativeQuery = true)
    void insertarTipoBebida(@Param("nombre") String nombre);
    
}