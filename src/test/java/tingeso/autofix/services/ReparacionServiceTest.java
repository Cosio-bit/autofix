// Source code is decompiled from a .class file using FernFlower decompiler.
package tingeso.autofix.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tingeso.autofix.entities.ReparacionEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReparacionServiceTest {

   @Autowired
   private ReparacionService reparacionService;
   ReparacionEntity reparacion = new ReparacionEntity();

   @Test
   void whenGuardarReparacion_thenReparacionGuardado() {
        this.reparacion.setFechaHoraIngreso(LocalDateTime.now());
        this.reparacion.setFechaHoraSalida(null);
        this.reparacion.setMontoTotal(null);
        this.reparacion.setTipoReparacion("1");
        this.reparacion.setIdVehiculo("1");

      ReparacionEntity reparacionEntity = this.reparacionService.guardarReparacion(reparacion);
      assertThat(reparacionEntity).isEqualTo(this.reparacion);
   }

   /*

   @Test
   void whenGuardarReparacionAndGetPatente_thenCorrect() {
      this.reparacion.setPatente("GHI789");
      this.reparacion.setMarca("Chevrolet");
      this.reparacion.setModelo("Cruze");
      this.reparacion.setAnnoFabricacion("2019");
      this.reparacion.setTipoReparacion("Sedan");
      this.reparacion.setTipoMotor("Gasolina");
      this.reparacion.setNroAsientos(5);
      this.reparacion.setKilometraje(20000);
      this.reparacionService.guardarReparacion(reparacion);
      String patente = this.reparacionService.obtenerPorPatente("GHI789").get().getPatente();
      assertThat(patente).isEqualTo("GHI789");
   }

   @Test
   void whenGuardarReparacionAndGetId_thenCorrect() {
      this.reparacion.setPatente("GHI789");
      this.reparacion.setMarca("Chevrolet");
      this.reparacion.setModelo("Cruze");
      this.reparacion.setAnnoFabricacion("2019");
      this.reparacion.setTipoReparacion("Sedan");
      this.reparacion.setTipoMotor("Gasolina");
      this.reparacion.setNroAsientos(5);
      this.reparacion.setKilometraje(20000);
      this.reparacionService.guardarReparacion(reparacion);
      Long id = this.reparacionService.obtenerPorId(1L).getId();
      assertThat(id).isEqualTo(1L);
   }

   @Test
   void whenGuardarReparacion_thenObtenerReparacions() {
      this.reparacion.setPatente("GHI789");
      this.reparacion.setMarca("Chevrolet");
      this.reparacion.setModelo("Cruze");
      this.reparacion.setAnnoFabricacion("2019");
      this.reparacion.setTipoReparacion("Sedan");
      this.reparacion.setTipoMotor("Gasolina");
      this.reparacion.setNroAsientos(5);
      this.reparacion.setKilometraje(20000);
      this.reparacionService.guardarReparacion(reparacion);

      ReparacionEntity reparacion1 = this.reparacionService.obtenerReparacions().get(4);
      assertThat(reparacion).isEqualTo(reparacion1);
   }

   @Test
   void whenGuardarReparacionAndUpdated_thenCorrect() {
      this.reparacion.setPatente("GHI789");
      this.reparacion.setMarca("Chevrolet");
      this.reparacion.setModelo("Cruze");
      this.reparacion.setAnnoFabricacion("2019");
      this.reparacion.setTipoReparacion("Sedan");
      this.reparacion.setTipoMotor("Gasolina");
      this.reparacion.setNroAsientos(5);
      this.reparacion.setKilometraje(20000);
      this.reparacionService.guardarReparacion(reparacion);

      ReparacionEntity reparacion2 = this.reparacionService.obtenerPorPatente("GHI789").get();
      reparacion2.setPatente("GHI788");
      this.reparacionService.updateReparacion(reparacion2);
      assertThat(reparacion2.getPatente()).isEqualTo("GHI788");
   }*/

}
