package tingeso.autofix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tingeso.autofix.entities.MarcaEntity;

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

        @Query(value = "SELECT * FROM marcas WHERE fecha_bono = :fechaBono AND nombre = :nombre", nativeQuery = true)
        Optional<MarcaEntity> findByFechaBonoMarca(@Param("fechaBono") String fechaBono, @Param("nombre") String nombre);

        @Query(value = "SELECT * FROM marcas WHERE descuento = :descuento", nativeQuery = true)
        Optional<MarcaEntity> findByDescuento(@Param("descuento") int descuento);

        @Query(value = "SELECT * FROM marcas WHERE cantidad_bonos = :cantidadBonos", nativeQuery = true)
        Optional<MarcaEntity> findByCantidadBonos(@Param("cantidadBonos") int cantidadBonos);
        
        //find all marcas
        @Query(value = "SELECT * FROM marcas", nativeQuery = true)
        List<MarcaEntity> findAllMarcas();



    }
    
