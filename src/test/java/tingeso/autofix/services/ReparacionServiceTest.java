// Source code is decompiled from a .class file using FernFlower decompiler.
package tingeso.autofix.services;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tingeso.autofix.entities.ReparacionEntity;
import tingeso.autofix.entities.VehiculoEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReparacionServiceTest {

   @Autowired
   private ReparacionService reparacionService;
   ReparacionEntity reparacion = new ReparacionEntity();


   @BeforeEach
   void setUp() {
      reparacion.setId(2L);
       reparacion.setFechaHoraIngreso(LocalDateTime.now());
       reparacion.setFechaHoraSalida(null);
       reparacion.setMontoTotal(null);
       reparacion.setTipoReparacion("1");
       reparacion.setIdVehiculo("1");
   }

   @Test
   void whenGuardarReparacion_thenReparacionGuardado() {
      ReparacionEntity reparacionEntity = this.reparacionService.guardarReparacion(reparacion);
      assertThat(reparacionEntity).isEqualTo(this.reparacion);
   }

   @Test
   void whenGuardarReparacionAndGetId_thenCorrect() {
      Long id = this.reparacionService.findById(2L).getId();
      assertThat(id).isEqualTo(2L);
   }

   @Test
   void whenGuardarReparaciones_thenObtenerReparaciones() {
      ReparacionEntity reparacionEntity = this.reparacionService.guardarReparacion(reparacion);
      ArrayList<ReparacionEntity> reparaciones = this.reparacionService.obtenerReparaciones();
      assertThat(reparaciones.size()).isEqualTo(1);
   }

   @Test
   void whenGuardarReparacion_thenObtenerReparacionesByVehiculo() {
       ReparacionEntity reparacionEntity = this.reparacionService.guardarReparacion(reparacion);
      ReparacionEntity reparacion1 = this.reparacionService.obtenerReparacionesPorIdVehiculo("1").get(1);
      assertThat(reparacion1.getFechaHoraIngreso()).isEqualToIgnoringHours(reparacion.getFechaHoraIngreso());
      assertThat(reparacion1.getTipoReparacion()).isEqualTo(reparacion.getTipoReparacion());
      assertThat(reparacion1.getIdVehiculo()).isEqualTo(reparacion.getIdVehiculo());
      assertThat(reparacion1.getId()).isEqualTo(reparacion.getId());
   }

   @Test
   void whenGuardarReparacion_thenUpdateReparacion() {
      ReparacionEntity reparacionEntity = this.reparacionService.guardarReparacion(reparacion);
      reparacion.setFechaHoraSalida(LocalDateTime.now());
      ReparacionEntity reparacion1 = this.reparacionService.updateReparacion(reparacion);
      assertThat(reparacion1.getFechaHoraSalida()).isEqualToIgnoringHours(reparacion.getFechaHoraSalida());
   }

   @Test
   void whenGuardarReparacion_thenDeleteReparacion() {
      ReparacionEntity reparacionEntity = this.reparacionService.guardarReparacion(reparacion);
      try {
         this.reparacionService.deleteReparacion(1L);
      } catch (Exception e) {
         e.printStackTrace();
      }
      assertThat(this.reparacionService.obtenerReparaciones().size()).isEqualTo(1);
   }

   @Test
   void whenGuardarReparacion_thenUpdateMontoTotal() {
      ReparacionEntity reparacionEntity = this.reparacionService.guardarReparacion(reparacion);
      ReparacionEntity reparacion1 = this.reparacionService.updateMontoTotal(reparacion).getLeft();
      assertThat(reparacion1.getMontoTotal()).isEqualTo(99000);
   }

   @Test
   void whenGuardarReparacion_thenObtenerReparacionesPorMarca() {
      ReparacionEntity reparacionEntity = this.reparacionService.guardarReparacion(reparacion);
      List<ReparacionEntity> reparaciones = this.reparacionService.obtenerReparacionesPorMarca("Chevrolet");
      assertThat(reparaciones.size()).isEqualTo(2);
   }

   @Test
   void whenGuardarReparacion_thenObtenerReparacionesPorTipoVehiculo() {
      ReparacionEntity reparacionEntity = this.reparacionService.guardarReparacion(reparacion);
      List<ReparacionEntity> reparaciones = this.reparacionService.obtenerReparacionesPorTipoVehiculo("Sedan");
      assertThat(reparaciones.size()).isEqualTo(1);
   }

   @Test
   void whenGuardarReparacion_thenObtenerReparacionesPorTipoMotor() {
      ReparacionEntity reparacionEntity = this.reparacionService.guardarReparacion(reparacion);
      List<ReparacionEntity> reparaciones = this.reparacionService.obtenerReparacionesPorTipoMotor("Gasolina");
      assertThat(reparaciones.size()).isEqualTo(2);
   }
   



}