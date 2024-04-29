// Source code is decompiled from a .class file using FernFlower decompiler.
package tingeso.autofix.services;

import tingeso.autofix.entities.VehiculoEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class VehiculoServiceTest {

   @Autowired
   private VehiculoService vehiculoService;
   VehiculoEntity vehiculo = new VehiculoEntity();

   @Test
   void whenGuardarVehiculo_thenVehiculoGuardado() {
      this.vehiculo.setPatente("GHI789");
      this.vehiculo.setMarca("Chevrolet");
      this.vehiculo.setModelo("Cruze");
      this.vehiculo.setAnnoFabricacion("2019");
      this.vehiculo.setTipoVehiculo("Sedan");
      this.vehiculo.setTipoMotor("Gasolina");
      this.vehiculo.setNroAsientos(5);
      this.vehiculo.setKilometraje(20000);
      VehiculoEntity vehiculoEntity = this.vehiculoService.guardarVehiculo(vehiculo);
      assertThat(vehiculoEntity).isEqualTo(this.vehiculo);
   }

   @Test
   void whenGuardarVehiculoAndGetPatente_thenCorrect() {
      this.vehiculo.setPatente("GHI789");
      this.vehiculo.setMarca("Chevrolet");
      this.vehiculo.setModelo("Cruze");
      this.vehiculo.setAnnoFabricacion("2019");
      this.vehiculo.setTipoVehiculo("Sedan");
      this.vehiculo.setTipoMotor("Gasolina");
      this.vehiculo.setNroAsientos(5);
      this.vehiculo.setKilometraje(20000);
      this.vehiculoService.guardarVehiculo(vehiculo);
      String patente = this.vehiculoService.obtenerPorPatente("GHI789").get().getPatente();
      assertThat(patente).isEqualTo("GHI789");
   }

   @Test
   void whenGuardarVehiculoAndGetId_thenCorrect() {
      this.vehiculo.setPatente("GHI789");
      this.vehiculo.setMarca("Chevrolet");
      this.vehiculo.setModelo("Cruze");
      this.vehiculo.setAnnoFabricacion("2019");
      this.vehiculo.setTipoVehiculo("Sedan");
      this.vehiculo.setTipoMotor("Gasolina");
      this.vehiculo.setNroAsientos(5);
      this.vehiculo.setKilometraje(20000);
      this.vehiculoService.guardarVehiculo(vehiculo);
      Long id = this.vehiculoService.obtenerPorId(1L).getId();
      assertThat(id).isEqualTo(1L);
   }

   @Test
   void whenGuardarVehiculo_thenObtenerVehiculos() {
      this.vehiculo.setPatente("GHI789");
      this.vehiculo.setMarca("Chevrolet");
      this.vehiculo.setModelo("Cruze");
      this.vehiculo.setAnnoFabricacion("2019");
      this.vehiculo.setTipoVehiculo("Sedan");
      this.vehiculo.setTipoMotor("Gasolina");
      this.vehiculo.setNroAsientos(5);
      this.vehiculo.setKilometraje(20000);
      this.vehiculoService.guardarVehiculo(vehiculo);

      VehiculoEntity vehiculo1 = this.vehiculoService.obtenerVehiculos().get(4);
      assertThat(vehiculo).isEqualTo(vehiculo1);
   }

   @Test
   void whenGuardarVehiculoAndUpdated_thenCorrect() {
      this.vehiculo.setPatente("GHI789");
      this.vehiculo.setMarca("Chevrolet");
      this.vehiculo.setModelo("Cruze");
      this.vehiculo.setAnnoFabricacion("2019");
      this.vehiculo.setTipoVehiculo("Sedan");
      this.vehiculo.setTipoMotor("Gasolina");
      this.vehiculo.setNroAsientos(5);
      this.vehiculo.setKilometraje(20000);
      this.vehiculoService.guardarVehiculo(vehiculo);

      VehiculoEntity vehiculo2 = this.vehiculoService.obtenerPorPatente("GHI789").get();
      vehiculo2.setPatente("GHI788");
      this.vehiculoService.updateVehiculo(vehiculo2);
      assertThat(vehiculo2.getPatente()).isEqualTo("GHI788");
   }

}
