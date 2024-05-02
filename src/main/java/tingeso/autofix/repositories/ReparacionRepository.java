package tingeso.autofix.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;
import tingeso.autofix.entities.ReparacionEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface ReparacionRepository extends JpaRepository<ReparacionEntity, Long> {
    //all queries
    @Query(value = "SELECT * FROM reparaciones WHERE id = :id", nativeQuery = true)
    Optional<ReparacionEntity> findById(@Param("id") Long id);

    @Query(value = "SELECT * FROM reparaciones WHERE id_vehiculo = :id_vehiculo", nativeQuery = true)
    Optional<ReparacionEntity> findByIdVehiculo(@Param("id_vehiculo") String id_vehiculo);

    @Query(value = "SELECT * FROM reparaciones WHERE tipo_reparacion = :tipoReparacion", nativeQuery = true)
    Optional<ReparacionEntity> findByTipoReparacion(@Param("tipoReparacion") String tipoReparacion);

    //find all reparaciones
    @Query(value = "SELECT * FROM reparaciones", nativeQuery = true)
    List<ReparacionEntity> findAllReparaciones();

    @Query(value = "SELECT * FROM reparaciones WHERE id_vehiculo = :id_vehiculo", nativeQuery = true)
    List<ReparacionEntity> findByVehiculoID(@Param("id_vehiculo") String id_vehiculo);



}
