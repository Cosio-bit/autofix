// Source code is decompiled from a .class file using FernFlower decompiler.
package tingeso.autofix.services;

import tingeso.autofix.entities.MarcaEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
class MarcaServiceTest {

   @Autowired
   private MarcaService marcaService;
   MarcaEntity marca = new MarcaEntity();

   @Test
   void whenGuardarMarca_thenMarcaGuardado() {
        this.marca.setCantidadBonos(3);
        this.marca.setDescuento(70000);
        this.marca.setFechaBono(LocalDateTime.now());
        this.marca.setNombre("Toyota");
      MarcaEntity marcaEntity = this.marcaService.guardarMarca(marca);
      assertThat(marcaEntity).isNotNull();
   }

   @Test
   void whenGuardarMarcaAndGetPatente_thenCorrect() {
       this.marca.setCantidadBonos(3);
       this.marca.setDescuento(70000);
       this.marca.setFechaBono(LocalDateTime.now());
       this.marca.setNombre("Toyota");
      this.marcaService.guardarMarca(marca);
      String patente = this.marcaService.findByNombre("Toyota").get().getNombre();
      assertThat(patente).isEqualTo("Toyota");
   }

   @Test
   void whenGuardarMarcaAndGetId_thenCorrect() {
       this.marca.setCantidadBonos(3);
       this.marca.setDescuento(70000);
       this.marca.setFechaBono(LocalDateTime.now());
       this.marca.setNombre("Toyota");
      this.marcaService.guardarMarca(marca);
      Long id = this.marcaService.findById(1L).getId();
      assertThat(id).isEqualTo(1L);
   }

   @Test
   void whenGuardarMarca_thenObtenerMarcas() {
       this.marca.setCantidadBonos(3);
       this.marca.setDescuento(70000);
       this.marca.setFechaBono(LocalDateTime.now());
       this.marca.setNombre("Toyota");
      this.marcaService.guardarMarca(marca);

      MarcaEntity marca1 = this.marcaService.obtenerMarcas().get(1);
      assertThat(marca).isEqualTo(marca1);
   }

   @Test
   void whenGuardarMarcaAndUpdated_thenCorrect() {
       this.marca.setCantidadBonos(3);
       this.marca.setDescuento(70000);
       this.marca.setFechaBono(LocalDateTime.now());
       this.marca.setNombre("Toyota");
      this.marcaService.guardarMarca(marca);

      MarcaEntity marca2 = this.marcaService.findByNombre("Toyota").get();
      marca2.setNombre("Kia");
      this.marcaService.updateMarca(marca2);
      assertThat(marca2.getNombre()).isEqualTo("Kia");
   }

}
