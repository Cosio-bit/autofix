package tingeso.autofix.repositories;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tingeso.autofix.entities.MarcaEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

    @Repository
    public interface MarcaRepository extends JpaRepository<MarcaEntity, Long> {
        //all queries
        @Query(value = "SELECT * FROM marcas WHERE id = :id", nativeQuery = true)
        Optional<MarcaEntity> findById(@Param("id") Long id);

        @Query(value = "SELECT * FROM marcas WHERE nombre = :nombre", nativeQuery = true)
        Optional<MarcaEntity> findByNombre(@Param("nombre") String nombre);

        @Query(value = "SELECT * FROM marcas WHERE fecha_bono = :fechaBono", nativeQuery = true)
        Optional<MarcaEntity> findByFechaBono(@Param("fechaBono") String fechaBono);

        @Query(value = "SELECT * FROM marcas WHERE cantidad_bonos = :cantidadBonos", nativeQuery = true)
        Optional<MarcaEntity> findByCantidadBonos(@Param("cantidadBonos") int cantidadBonos);
        
        //find all marcas
        @Query(value = "SELECT * FROM marcas", nativeQuery = true)
        List<MarcaEntity> findAllMarcas();

        //find by nombre and fechaBono with timestamp
        @Query(value = "SELECT * FROM marcas WHERE nombre = :nombre AND fecha_bono = :fechaBono", nativeQuery = true)
        MarcaEntity findByNombreAndFechaBono( @Param("fechaBono") LocalDateTime fechaBono, @Param("nombre") String nombre);





    }
    
