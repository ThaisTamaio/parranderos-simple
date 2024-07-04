package net.javaguides.banking.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import net.javaguides.banking.modelo.Gustan;

public interface GustanRepository  extends JpaRepository<Gustan, Integer>{

    @Query(value = "SELECT * FROM gustan", nativeQuery = true)
    Collection<Gustan> darGustan();

    @Query(value = "SELECT * FROM gustan WHERE id_bebedor = :id_bebedor AND id_bebida = :id_bebida", nativeQuery = true)
    Gustan darGustanPorId(@Param("id_bebedor") Integer id_bebedor, @Param("id_bebida") Integer id_bebida);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM gustan WHERE id_bebedor = :id_bebedor AND id_bebida = :id_bebida", nativeQuery = true)
    void eliminarGustan(@Param("id_bebedor") Integer id_bebedor, @Param("id_bebida") Integer id_bebida);

    @Modifying
    @Transactional
    @Query(value = "UPDATE gustan SET id_bebedor = :id_bebedor_actualizado, id_bebida = :id_bebida_actualizado WHERE id_bebedor = :id_bebedor AND id_bebida = :id_bebida", nativeQuery = true)
    void actualizarGustan(@Param("id_bebedor") Integer id_bebedor, @Param("id_bebida") Integer id_bebida, @Param("id_bebedor_actualizado") Integer id_bebedor_actualizado, @Param("id_bebida_actualizado") Integer id_bebida_actualizado);
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO gustan (id_bebedor, id_bebida) VALUES (:id_bebedor, :id_bebida)", nativeQuery = true)
    void insertarGustan(@Param("id_bebedor") Integer id_bebedor, @Param("id_bebida") Integer id_bebida);

}