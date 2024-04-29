// Source code is decompiled from a .class file using FernFlower decompiler.
package tingeso.autofix.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import tingeso.autofix.entities.ReparacionEntity;
import tingeso.autofix.entities.VehiculoEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@DataJpaTest
@ActiveProfiles({"test"})
class ReparacionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReparacionRepository reparacionRepository;

    VehiculoEntity vehiculo = new VehiculoEntity();
    
    @BeforeEach
    void setUp() {
        vehiculo.setPatente("GHI789");
        vehiculo.setMarca("Chevrolet");
        vehiculo.setModelo("Cruze");
        vehiculo.setAnnoFabricacion("2019");
        vehiculo.setTipoVehiculo("Sedan");
        vehiculo.setTipoMotor("Gasolina");
        vehiculo.setNroAsientos(5);
        vehiculo.setKilometraje(20000);
        entityManager.persistAndFlush(vehiculo);
    }

    @Test
    public void whenFindByTipoReparacion_thenReturnReparacion() {
        // given
        ReparacionEntity reparacion = new ReparacionEntity();
        reparacion.setFechaHoraIngreso(LocalDateTime.now());
        reparacion.setFechaHoraSalida(null);
        reparacion.setFechaHoraRetiro(null);
        reparacion.setMontoTotal(null);
        reparacion.setTipoReparacion("1");
        reparacion.setIdVehiculo("1");
        entityManager.persistAndFlush(reparacion);
        reparacionRepository.save(reparacion);

        // when
        Optional<ReparacionEntity> found = reparacionRepository.findByTipoReparacion(reparacion.getTipoReparacion());

        // then
        assertThat(found.get().getTipoReparacion()).isEqualTo(reparacion.getTipoReparacion());
    }

    @Test
    public void whenFindByIdVehiculo_thenReturnReparacion() {
        // given
        ReparacionEntity reparacion = new ReparacionEntity();
        reparacion.setFechaHoraIngreso(LocalDateTime.now());
        reparacion.setFechaHoraSalida(LocalDateTime.now().plusDays(1));
        reparacion.setFechaHoraRetiro(LocalDateTime.now().plusDays(3));
        reparacion.setMontoTotal(null);
        reparacion.setTipoReparacion("1");
        reparacion.setIdVehiculo("1");
        entityManager.persistAndFlush(reparacion);
        reparacionRepository.save(reparacion);

        // when
        Optional<ReparacionEntity> found = reparacionRepository.findByIdVehiculo(reparacion.getIdVehiculo());

        // then
        assertThat(found.get().getIdVehiculo()).isEqualTo(reparacion.getIdVehiculo());
    }

    @Test
    public void findall() {
        // given
        ReparacionEntity reparacion1 = new ReparacionEntity();
        reparacion1.setFechaHoraIngreso(LocalDateTime.now());
        reparacion1.setFechaHoraSalida(null);
        reparacion1.setFechaHoraRetiro(null);
        reparacion1.setMontoTotal(null);
        reparacion1.setTipoReparacion("1");
        reparacion1.setIdVehiculo("1");
        entityManager.persistAndFlush(reparacion1);
        reparacionRepository.save(reparacion1);

        ReparacionEntity reparacion2 = new ReparacionEntity();
        reparacion2.setFechaHoraIngreso(LocalDateTime.now());
        reparacion2.setFechaHoraSalida(null);
        reparacion2.setFechaHoraRetiro(null);
        reparacion2.setMontoTotal(null);
        reparacion2.setTipoReparacion("2");
        reparacion2.setIdVehiculo("1");
        entityManager.persistAndFlush(reparacion2);
        reparacionRepository.save(reparacion2);

        // when
        List<ReparacionEntity> reparacions = reparacionRepository.findAllReparaciones();

        // then
        assertThat(reparacions.size()).isEqualTo(2);
    }

}