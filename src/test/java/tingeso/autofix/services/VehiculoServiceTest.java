// Source code is decompiled from a .class file using FernFlower decompiler.
package tingeso.autofix.services;

import tingeso.autofix.entities.VehiculoEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class VehiculoServiceTest {

   @Autowired
   private VehiculoService vehiculoService;
   VehiculoEntity vehiculo = new VehiculoEntity();

         @BeforeEach
      void setUp() {
          vehiculo.setPatente("GHI700");
          vehiculo.setMarca("Chevrolet");
          vehiculo.setModelo("Cruze");
          vehiculo.setAnnoFabricacion("1980");
          vehiculo.setTipoVehiculo("Sedan");
          vehiculo.setTipoMotor("Gasolina");
          vehiculo.setNroAsientos(5);
          vehiculo.setKilometraje(20000);}

   @Test
   void whenGuardarVehiculo_thenVehiculoGuardado() {
      VehiculoEntity vehiculoEntity = this.vehiculoService.guardarVehiculo(vehiculo);
      assertThat(vehiculoEntity).isEqualTo(this.vehiculo);
   }

   @Test
   void whenGuardarVehiculoAndGetPatente_thenCorrect() {
      VehiculoEntity vehiculoEntity = this.vehiculoService.guardarVehiculo(vehiculo);
      String patente = this.vehiculoService.obtenerPorPatente("GHI700").get().getPatente();
      assertThat(patente).isEqualTo("GHI700");
   }

   @Test
   void whenGuardarVehiculoAndGetId_thenCorrect() {
      VehiculoEntity vehiculoEntity = this.vehiculoService.guardarVehiculo(vehiculo);
      Long id = this.vehiculoService.obtenerPorId(1L).getId();
      assertThat(id).isEqualTo(1L);
   }

   @Test
   void whenGuardarVehiculo_thenObtenerVehiculos() {
      VehiculoEntity vehiculoEntity = this.vehiculoService.guardarVehiculo(vehiculo);
      VehiculoEntity vehiculo1 = this.vehiculoService.obtenerVehiculos().get(6);
      assertThat(vehiculo).isEqualTo(vehiculo1);
   }

   @Test
   void whenGuardarVehiculoAndUpdated_thenCorrect() {
      VehiculoEntity vehiculoEntity = this.vehiculoService.guardarVehiculo(vehiculo);
      VehiculoEntity vehiculo2= this.vehiculoService.obtenerPorPatente("GHI700").get();
      vehiculo2.setPatente("GHI380");
      this.vehiculoService.updateVehiculo(vehiculo2);
      assertThat(vehiculo2.getPatente()).isEqualTo("GHI380");
   }


   @Test
   void whenGuardarVehiculoAndMarca_thenCorrect() {
      VehiculoEntity vehiculo2 = this.vehiculoService.obtenerVehiculosPorMarca("Chevrolet").get(4);
      assertThat(vehiculo.getMarca()).isEqualTo(vehiculo2.getMarca());
   }

   @Test
   void whenGuardarVehiculoAndModelo_thenCorrect() {
      VehiculoEntity vehiculo2 = this.vehiculoService.obtenerVehiculosPorTipoMotor("Gasolina").get(4);
      assertThat(vehiculo.getTipoMotor()).isEqualTo(vehiculo2.getTipoMotor());
   }

   @Test
   void whenGuardarVehiculoAndTipoVehiculo_thenCorrect() {
      VehiculoEntity vehiculo2 = this.vehiculoService.obtenerVehiculosPorTipoVehiculo("Sedan").get(4);
      assertThat(vehiculo.getTipoMotor()).isEqualTo(vehiculo2.getTipoMotor());
   }

   @Test
   void whenDeleteVehiculo_thenCorrect() {
      VehiculoEntity vehiculoEntity = this.vehiculoService.guardarVehiculo(vehiculo);
      try {
         this.vehiculoService.deleteVehiculo(vehiculoEntity.getId());
      } catch (Exception e) {
         e.printStackTrace();
      }
      assertThat(this.vehiculoService.obtenerVehiculos().size()).isEqualTo(5);
   }

}
