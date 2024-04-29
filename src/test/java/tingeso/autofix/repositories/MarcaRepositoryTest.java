// Source code is decompiled from a .class file using FernFlower decompiler.
package tingeso.autofix.repositories;

import tingeso.autofix.entities.MarcaEntity;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles({"test"})
class MarcaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MarcaRepository marcaRepository;

    @Test
    public void whenFindByPatente_thenReturnMarca() {
        // given
        MarcaEntity marca = new MarcaEntity();
        marca.setCantidadBonos(3);
        marca.setDescuento(70000);
        marca.setFechaBono(LocalDateTime.now());
        marca.setNombre("Toyota");
        entityManager.persistAndFlush(marca);
        marcaRepository.save(marca);

        // when
        Optional<MarcaEntity> found = marcaRepository.findByNombre(marca.getNombre());

        // then
        assertThat(found.get().getNombre()).isEqualTo(marca.getNombre());
    }
/*
    @Test
    public void whenFindByMarca_thenReturnMarcas() {
        // given
        MarcaEntity marca1 = new MarcaEntity();
        marca1.setPatente("GHI789");
        marca1.setMarca("Chevrolet");
        marca1.setModelo("Cruze");
        marca1.setAnnoFabricacion("2019");
        marca1.setTipoMarca("Sedan");
        marca1.setTipoMotor("Gasolina");
        marca1.setNroAsientos(5);
        marca1.setKilometraje(20000);
        marcaRepository.save(marca1);

        // Crear el segundo objeto MarcaEntity para probar
        MarcaEntity marca2 = new MarcaEntity();
        marca2.setPatente("ABC123");
        marca2.setMarca("Chevrolet");
        marca2.setModelo("Corolla");
        marca2.setAnnoFabricacion("2020");
        marca2.setTipoMarca("Sedan");
        marca2.setTipoMotor("Híbrido");
        marca2.setNroAsientos(5);
        marca2.setKilometraje(15000);
        marcaRepository.save(marca2);

        entityManager.persist(marca1);
        entityManager.persist(marca2);
        entityManager.flush();

        // when
        List<MarcaEntity> foundMarcas = marcaRepository.findByMarca("Chevrolet");

        // then
        assertThat(foundMarcas).hasSize(2).extracting(MarcaEntity::getMarca).containsOnly("Chevrolet");
    }

    @Test
    public void whenFindByAnnoFabricacion_thenReturnMarcas() {
         // given
         MarcaEntity marca1 = new MarcaEntity();
         marca1.setPatente("GHI789");
         marca1.setMarca("Chevrolet");
         marca1.setModelo("Cruze");
         marca1.setAnnoFabricacion("2019");
         marca1.setTipoMarca("Sedan");
         marca1.setTipoMotor("Gasolina");
         marca1.setNroAsientos(5);
         marca1.setKilometraje(20000);
         marcaRepository.save(marca1);
 
         // Crear el segundo objeto MarcaEntity para probar
         MarcaEntity marca2 = new MarcaEntity();
         marca2.setPatente("ABC123");
         marca2.setMarca("Toyota");
         marca2.setModelo("Corolla");
         marca2.setAnnoFabricacion("2020");
         marca2.setTipoMarca("Sedan");
         marca2.setTipoMotor("Híbrido");
         marca2.setNroAsientos(5);
         marca2.setKilometraje(15000);
         marcaRepository.save(marca2);
 
         entityManager.persist(marca1);
         entityManager.persist(marca2);
         entityManager.flush();

        // when
        Optional<MarcaEntity> foundMarcas = marcaRepository.findByAnnoFabricacion("2020");
        List<MarcaEntity> marcas = Collections.singletonList(foundMarcas.get());

        // then
        assertThat(marcas).hasSize(1).extracting(MarcaEntity::getAnnoFabricacion).containsOnly("2020");
    }

    @Test
    public void whenFindByTipoMarca_thenReturnMarcas() {
        // given
        MarcaEntity marca1 = new MarcaEntity();
        marca1.setPatente("GHI789");
        marca1.setMarca("Chevrolet");
        marca1.setModelo("Cruze");
        marca1.setAnnoFabricacion("2019");
        marca1.setTipoMarca("Sedan");
        marca1.setTipoMotor("Gasolina");
        marca1.setNroAsientos(5);
        marca1.setKilometraje(20000);
        marcaRepository.save(marca1);

        // Crear el segundo objeto MarcaEntity para probar
        MarcaEntity marca2 = new MarcaEntity();
        marca2.setPatente("ABC123");
        marca2.setMarca("Toyota");
        marca2.setModelo("Corolla");
        marca2.setAnnoFabricacion("2020");
        marca2.setTipoMarca("Sedan");
        marca2.setTipoMotor("Híbrido");
        marca2.setNroAsientos(5);
        marca2.setKilometraje(15000);
        marcaRepository.save(marca2);

        entityManager.persist(marca1);
        entityManager.persist(marca2);
        entityManager.flush();

       // when
       List<MarcaEntity> foundMarcas = marcaRepository.findByTipoMarca("Sedan");

        // then
        assertThat(foundMarcas).hasSize(2).extracting(MarcaEntity::getTipoMarca).containsOnly("Sedan");
    }

    @Test
    public void whenFindByTipoMotor_thenReturnMarca() {
        // given
        MarcaEntity marca1 = new MarcaEntity();
        marca1.setPatente("GHI789");
        marca1.setMarca("Chevrolet");
        marca1.setModelo("Cruze");
        marca1.setAnnoFabricacion("2019");
        marca1.setTipoMarca("Sedan");
        marca1.setTipoMotor("Gasolina");
        marca1.setNroAsientos(5);
        marca1.setKilometraje(20000);
        marcaRepository.save(marca1);

        // Crear el segundo objeto MarcaEntity para probar
        MarcaEntity marca2 = new MarcaEntity();
        marca2.setPatente("ABC123");
        marca2.setMarca("Toyota");
        marca2.setModelo("Corolla");
        marca2.setAnnoFabricacion("2020");
        marca2.setTipoMarca("Sedan");
        marca2.setTipoMotor("Híbrido");
        marca2.setNroAsientos(5);
        marca2.setKilometraje(15000);
        marcaRepository.save(marca2);

        entityManager.persist(marca1);
        entityManager.persist(marca2);
        entityManager.flush();

       // when
       Optional<MarcaEntity> foundMarcas = marcaRepository.findByTipoMotor("Híbrido");
       List<MarcaEntity> marcas = Collections.singletonList(foundMarcas.get());

        // then
        assertThat(marcas).hasSize(1).extracting(MarcaEntity::getTipoMotor).containsOnly("Híbrido");
    }


    @Test
    public void whenFindByNroAsientos_thenReturnMarcas() {
        // given
        MarcaEntity marca1 = new MarcaEntity();
        marca1.setPatente("GHI789");
        marca1.setMarca("Chevrolet");
        marca1.setModelo("Cruze");
        marca1.setAnnoFabricacion("2019");
        marca1.setTipoMarca("Sedan");
        marca1.setTipoMotor("Gasolina");
        marca1.setNroAsientos(5);
        marca1.setKilometraje(20000);
        marcaRepository.save(marca1);
    
        // Crear el segundo objeto MarcaEntity para probar
        MarcaEntity marca2 = new MarcaEntity();
        marca2.setPatente("ABC123");
        marca2.setMarca("Toyota");
        marca2.setModelo("Corolla");
        marca2.setAnnoFabricacion("2020");
        marca2.setTipoMarca("Sedan");
        marca2.setTipoMotor("Híbrido");
        marca2.setNroAsientos(5);
        marca2.setKilometraje(15000);
        marcaRepository.save(marca2);
    
        entityManager.flush();
    
        // when
        List<MarcaEntity> foundMarcas = marcaRepository.findByNroAsientos(5);
    
        // then
        assertThat(foundMarcas.size()).isEqualTo(2); // Check if 2 vehicles with 5 seats are found
        assertThat(foundMarcas.get(0).getNroAsientos()).isEqualTo(5); // Check if the first vehicle has 5 seats
        assertThat(foundMarcas.get(1).getNroAsientos()).isEqualTo(5); // Check if the second vehicle has 5 seats
    }
    


    @Test
    public void whenFindByKilometraje_thenReturnMarcas() {
        // given
        MarcaEntity marca1 = new MarcaEntity();
        marca1.setPatente("GHI789");
        marca1.setMarca("Chevrolet");
        marca1.setModelo("Cruze");
        marca1.setAnnoFabricacion("2019");
        marca1.setTipoMarca("Sedan");
        marca1.setTipoMotor("Gasolina");
        marca1.setNroAsientos(5);
        marca1.setKilometraje(20000);
        marcaRepository.save(marca1);

        // Crear el segundo objeto MarcaEntity para probar
        MarcaEntity marca2 = new MarcaEntity();
        marca2.setPatente("ABC123");
        marca2.setMarca("Toyota");
        marca2.setModelo("Corolla");
        marca2.setAnnoFabricacion("2020");
        marca2.setTipoMarca("Sedan");
        marca2.setTipoMotor("Híbrido");
        marca2.setNroAsientos(5);
        marca2.setKilometraje(15000);
        marcaRepository.save(marca2);

        entityManager.persist(marca1);
        entityManager.persist(marca2);
        entityManager.flush();

       // when
       Optional<MarcaEntity> foundMarcas = marcaRepository.findByKilometraje(20000);
       List<MarcaEntity> marcas = Collections.singletonList(foundMarcas.get());

        // then
        assertThat(marcas).hasSize(1).extracting(MarcaEntity::getKilometraje).containsOnly(20000);
    }
*/
}