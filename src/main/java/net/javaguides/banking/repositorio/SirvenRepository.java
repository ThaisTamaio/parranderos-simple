package net.javaguides.banking.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import net.javaguides.banking.modelo.Sirven;

public interface SirvenRepository  extends JpaRepository<Sirven, Integer>{

    @Query(value = "SELECT * FROM sirven", nativeQuery = true)
    Collection<Sirven> darSirven();

    @Query(value = "SELECT * FROM sirven WHERE id_bar = :id_bar AND id_bebida = :id_bebida AND horario = :horario", nativeQuery = true)
    Sirven darSirvenPorId(@Param("id_bar") Integer id_bar, @Param("id_bebida") Integer id_bebida, @Param("horario") String horario);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sirven WHERE id_bar = :id_bar AND id_bebida = :id_bebida AND horario = :horario", nativeQuery = true)
    void eliminarSirven(@Param("id_bar") Integer id_bar, @Param("id_bebida") Integer id_bebida, @Param("horario") String horario);

    @Modifying  
    @Transactional
    @Query(value = "UPDATE sirven SET id_bar = :id_bar_actualizado, id_bebida = :id_bebida_actualizado, horario = :horario_actualizado WHERE id_bar = :id_bar AND id_bebida = :id_bebida AND horario = :horario", nativeQuery = true)
    void actualizarSirven(@Param("id_bar") Integer id_bar, @Param("id_bebida") Integer id_bebida, @Param("horario") String horario, @Param("id_bar_actualizado") Integer id_bar_actualizado, @Param("id_bebida_actualizado") Integer id_bebida_actualizado, @Param("horario_actualizado") String horario_actualizado);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO sirven (id_bar, id_bebida, horario) VALUES (:id_bar, :id_bebida, :horario)", nativeQuery = true)
    void insertarSirven(@Param("id_bar") Integer id_bar, @Param("id_bebida") Integer id_bebida, @Param("horario") String horario);

}