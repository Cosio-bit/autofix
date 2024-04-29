// Source code is decompiled from a .class file using FernFlower decompiler.
package tingeso.autofix.repositories;

import tingeso.autofix.entities.VehiculoEntity;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles({"test"})
class VehiculoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Test
    public void whenFindByPatente_thenReturnVehiculo() {
        // given
        VehiculoEntity vehiculo = new VehiculoEntity();
        vehiculo.setPatente("GHI789");
        vehiculo.setMarca("Chevrolet");
        vehiculo.setModelo("Cruze");
        vehiculo.setAnnoFabricacion("2019");
        vehiculo.setTipoVehiculo("Sedan");
        vehiculo.setTipoMotor("Gasolina");
        vehiculo.setNroAsientos(5);
        vehiculo.setKilometraje(20000);
        entityManager.persistAndFlush(vehiculo);
        vehiculoRepository.save(vehiculo);

        // when
        Optional<VehiculoEntity> found = vehiculoRepository.findByPatente(vehiculo.getPatente());

        // then
        assertThat(found.get().getPatente()).isEqualTo(vehiculo.getPatente());
    }

    @Test
    public void whenFindByMarca_thenReturnVehiculos() {
        // given
        VehiculoEntity vehiculo1 = new VehiculoEntity();
        vehiculo1.setPatente("GHI789");
        vehiculo1.setMarca("Chevrolet");
        vehiculo1.setModelo("Cruze");
        vehiculo1.setAnnoFabricacion("2019");
        vehiculo1.setTipoVehiculo("Sedan");
        vehiculo1.setTipoMotor("Gasolina");
        vehiculo1.setNroAsientos(5);
        vehiculo1.setKilometraje(20000);
        vehiculoRepository.save(vehiculo1);

        // Crear el segundo objeto VehiculoEntity para probar
        VehiculoEntity vehiculo2 = new VehiculoEntity();
        vehiculo2.setPatente("ABC123");
        vehiculo2.setMarca("Chevrolet");
        vehiculo2.setModelo("Corolla");
        vehiculo2.setAnnoFabricacion("2020");
        vehiculo2.setTipoVehiculo("Sedan");
        vehiculo2.setTipoMotor("Híbrido");
        vehiculo2.setNroAsientos(5);
        vehiculo2.setKilometraje(15000);
        vehiculoRepository.save(vehiculo2);

        entityManager.persist(vehiculo1);
        entityManager.persist(vehiculo2);
        entityManager.flush();

        // when
        List<VehiculoEntity> foundVehiculos = vehiculoRepository.findByMarca("Chevrolet");

        // then
        assertThat(foundVehiculos).hasSize(2).extracting(VehiculoEntity::getMarca).containsOnly("Chevrolet");
    }

    @Test
    public void whenFindByAnnoFabricacion_thenReturnVehiculos() {
         // given
         VehiculoEntity vehiculo1 = new VehiculoEntity();
         vehiculo1.setPatente("GHI789");
         vehiculo1.setMarca("Chevrolet");
         vehiculo1.setModelo("Cruze");
         vehiculo1.setAnnoFabricacion("2019");
         vehiculo1.setTipoVehiculo("Sedan");
         vehiculo1.setTipoMotor("Gasolina");
         vehiculo1.setNroAsientos(5);
         vehiculo1.setKilometraje(20000);
         vehiculoRepository.save(vehiculo1);
 
         // Crear el segundo objeto VehiculoEntity para probar
         VehiculoEntity vehiculo2 = new VehiculoEntity();
         vehiculo2.setPatente("ABC123");
         vehiculo2.setMarca("Toyota");
         vehiculo2.setModelo("Corolla");
         vehiculo2.setAnnoFabricacion("2020");
         vehiculo2.setTipoVehiculo("Sedan");
         vehiculo2.setTipoMotor("Híbrido");
         vehiculo2.setNroAsientos(5);
         vehiculo2.setKilometraje(15000);
         vehiculoRepository.save(vehiculo2);
 
         entityManager.persist(vehiculo1);
         entityManager.persist(vehiculo2);
         entityManager.flush();

        // when
        Optional<VehiculoEntity> foundVehiculos = vehiculoRepository.findByAnnoFabricacion("2020");
        List<VehiculoEntity> vehiculos = Collections.singletonList(foundVehiculos.get());

        // then
        assertThat(vehiculos).hasSize(1).extracting(VehiculoEntity::getAnnoFabricacion).containsOnly("2020");
    }

    @Test
    public void whenFindByTipoVehiculo_thenReturnVehiculos() {
        // given
        VehiculoEntity vehiculo1 = new VehiculoEntity();
        vehiculo1.setPatente("GHI789");
        vehiculo1.setMarca("Chevrolet");
        vehiculo1.setModelo("Cruze");
        vehiculo1.setAnnoFabricacion("2019");
        vehiculo1.setTipoVehiculo("Sedan");
        vehiculo1.setTipoMotor("Gasolina");
        vehiculo1.setNroAsientos(5);
        vehiculo1.setKilometraje(20000);
        vehiculoRepository.save(vehiculo1);

        // Crear el segundo objeto VehiculoEntity para probar
        VehiculoEntity vehiculo2 = new VehiculoEntity();
        vehiculo2.setPatente("ABC123");
        vehiculo2.setMarca("Toyota");
        vehiculo2.setModelo("Corolla");
        vehiculo2.setAnnoFabricacion("2020");
        vehiculo2.setTipoVehiculo("Sedan");
        vehiculo2.setTipoMotor("Híbrido");
        vehiculo2.setNroAsientos(5);
        vehiculo2.setKilometraje(15000);
        vehiculoRepository.save(vehiculo2);

        entityManager.persist(vehiculo1);
        entityManager.persist(vehiculo2);
        entityManager.flush();

       // when
       List<VehiculoEntity> foundVehiculos = vehiculoRepository.findByTipoVehiculo("Sedan");

        // then
        assertThat(foundVehiculos).hasSize(2).extracting(VehiculoEntity::getTipoVehiculo).containsOnly("Sedan");
    }

    @Test
    public void whenFindByTipoMotor_thenReturnVehiculo() {
        // given
        VehiculoEntity vehiculo1 = new VehiculoEntity();
        vehiculo1.setPatente("GHI789");
        vehiculo1.setMarca("Chevrolet");
        vehiculo1.setModelo("Cruze");
        vehiculo1.setAnnoFabricacion("2019");
        vehiculo1.setTipoVehiculo("Sedan");
        vehiculo1.setTipoMotor("Gasolina");
        vehiculo1.setNroAsientos(5);
        vehiculo1.setKilometraje(20000);
        vehiculoRepository.save(vehiculo1);

        // Crear el segundo objeto VehiculoEntity para probar
        VehiculoEntity vehiculo2 = new VehiculoEntity();
        vehiculo2.setPatente("ABC123");
        vehiculo2.setMarca("Toyota");
        vehiculo2.setModelo("Corolla");
        vehiculo2.setAnnoFabricacion("2020");
        vehiculo2.setTipoVehiculo("Sedan");
        vehiculo2.setTipoMotor("Híbrido");
        vehiculo2.setNroAsientos(5);
        vehiculo2.setKilometraje(15000);
        vehiculoRepository.save(vehiculo2);

        entityManager.persist(vehiculo1);
        entityManager.persist(vehiculo2);
        entityManager.flush();

       // when
       Optional<VehiculoEntity> foundVehiculos = vehiculoRepository.findByTipoMotor("Híbrido");
       List<VehiculoEntity> vehiculos = Collections.singletonList(foundVehiculos.get());

        // then
        assertThat(vehiculos).hasSize(1).extracting(VehiculoEntity::getTipoMotor).containsOnly("Híbrido");
    }


    @Test
    public void whenFindByNroAsientos_thenReturnVehiculos() {
        // given
        VehiculoEntity vehiculo1 = new VehiculoEntity();
        vehiculo1.setPatente("GHI789");
        vehiculo1.setMarca("Chevrolet");
        vehiculo1.setModelo("Cruze");
        vehiculo1.setAnnoFabricacion("2019");
        vehiculo1.setTipoVehiculo("Sedan");
        vehiculo1.setTipoMotor("Gasolina");
        vehiculo1.setNroAsientos(5);
        vehiculo1.setKilometraje(20000);
        vehiculoRepository.save(vehiculo1);
    
        // Crear el segundo objeto VehiculoEntity para probar
        VehiculoEntity vehiculo2 = new VehiculoEntity();
        vehiculo2.setPatente("ABC123");
        vehiculo2.setMarca("Toyota");
        vehiculo2.setModelo("Corolla");
        vehiculo2.setAnnoFabricacion("2020");
        vehiculo2.setTipoVehiculo("Sedan");
        vehiculo2.setTipoMotor("Híbrido");
        vehiculo2.setNroAsientos(5);
        vehiculo2.setKilometraje(15000);
        vehiculoRepository.save(vehiculo2);
    
        entityManager.flush();
    
        // when
        List<VehiculoEntity> foundVehiculos = vehiculoRepository.findByNroAsientos(5);
    
        // then
        assertThat(foundVehiculos.size()).isEqualTo(2); // Check if 2 vehicles with 5 seats are found
        assertThat(foundVehiculos.get(0).getNroAsientos()).isEqualTo(5); // Check if the first vehicle has 5 seats
        assertThat(foundVehiculos.get(1).getNroAsientos()).isEqualTo(5); // Check if the second vehicle has 5 seats
    }
    


    @Test
    public void whenFindByKilometraje_thenReturnVehiculos() {
        // given
        VehiculoEntity vehiculo1 = new VehiculoEntity();
        vehiculo1.setPatente("GHI789");
        vehiculo1.setMarca("Chevrolet");
        vehiculo1.setModelo("Cruze");
        vehiculo1.setAnnoFabricacion("2019");
        vehiculo1.setTipoVehiculo("Sedan");
        vehiculo1.setTipoMotor("Gasolina");
        vehiculo1.setNroAsientos(5);
        vehiculo1.setKilometraje(20000);
        vehiculoRepository.save(vehiculo1);

        // Crear el segundo objeto VehiculoEntity para probar
        VehiculoEntity vehiculo2 = new VehiculoEntity();
        vehiculo2.setPatente("ABC123");
        vehiculo2.setMarca("Toyota");
        vehiculo2.setModelo("Corolla");
        vehiculo2.setAnnoFabricacion("2020");
        vehiculo2.setTipoVehiculo("Sedan");
        vehiculo2.setTipoMotor("Híbrido");
        vehiculo2.setNroAsientos(5);
        vehiculo2.setKilometraje(15000);
        vehiculoRepository.save(vehiculo2);

        entityManager.persist(vehiculo1);
        entityManager.persist(vehiculo2);
        entityManager.flush();

       // when
       Optional<VehiculoEntity> foundVehiculos = vehiculoRepository.findByKilometraje(20000);
       List<VehiculoEntity> vehiculos = Collections.singletonList(foundVehiculos.get());

        // then
        assertThat(vehiculos).hasSize(1).extracting(VehiculoEntity::getKilometraje).containsOnly(20000);
    }

}