// Source code is decompiled from a .class file using FernFlower decompiler.
package tingeso.autofix.services;

import tingeso.autofix.entities.VehiculoEntity;
import tingeso.autofix.services.VehiculoService;
import tingeso.autofix.repositories.VehiculoRepository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VehiculoServiceTest {
   VehiculoService vehiculoService = new VehiculoService();
   VehiculoEntity vehiculo = new VehiculoEntity();
   VehiculoRepository vehiculoRepository = Mockito.mock(VehiculoRepository.class);

   VehiculoServiceTest() {
   }

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
      Assertions.assertThat(vehiculoEntity).isEqualTo(this.vehiculo);
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
      Assertions.assertThat(patente).isEqualTo("GHI789");
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
      Assertions.assertThat(id).isEqualTo(1L);
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

      VehiculoEntity vehiculo1 = this.vehiculoService.obtenerVehiculos().get(0);
      Assertions.assertThat(vehiculo).isEqualTo(vehiculo1);
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
      Assertions.assertThat(vehiculo2.getPatente()).isEqualTo("GHI788");
   }

}
