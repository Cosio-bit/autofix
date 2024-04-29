// Source code is decompiled from a .class file using FernFlower decompiler.
package tingeso.autofix.services;

import org.assertj.core.data.Percentage;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import tingeso.autofix.entities.MarcaEntity;
import tingeso.autofix.entities.ReparacionEntity;
import tingeso.autofix.entities.VehiculoEntity;
import tingeso.autofix.repositories.ReparacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
class PagoServiceTest {

    @Autowired
    ReparacionRepository reparacionRepository;

    @Autowired
    MarcaService marcaService;

    @Autowired
    VehiculoService vehiculoService;

    @Autowired
    PagoService pagoService;

    VehiculoEntity vehiculo = new VehiculoEntity();
    ReparacionEntity reparacion = new ReparacionEntity();
    MarcaEntity marca = new MarcaEntity();

    @BeforeEach
    void setUp() {
        this.vehiculo.setPatente("GHI789");
        this.vehiculo.setMarca("Chevrolet");
        this.vehiculo.setModelo("Cruze");
        this.vehiculo.setAnnoFabricacion("1980");
        this.vehiculo.setTipoVehiculo("Sedan");
        this.vehiculo.setTipoMotor("Gasolina");
        this.vehiculo.setNroAsientos(5);
        this.vehiculo.setKilometraje(200000);
        this.vehiculoService.guardarVehiculo(vehiculo);


        this.reparacion.setFechaHoraIngreso(LocalDateTime.now());
        this.reparacion.setFechaHoraSalida(LocalDateTime.now().plusDays(1));
        this.reparacion.setFechaHoraRetiro(LocalDateTime.now().plusDays(3));
        this.reparacion.setMontoTotal(null);
        this.reparacion.setTipoReparacion(",0,1");
        this.reparacion.setIdVehiculo("1");
        this.reparacionRepository.save(reparacion);
    }

    @Test
    void whenCalcularMontoTotal_thenMontoTotal() {
        double pago = pagoService.precioReparacionVSMotor(reparacion);
        assertThat(pago).isEqualTo(250000);
    }

    @Test
    void whenCantidadReparaciones_thenDescuento() {
        double descuento = pagoService.descuentoCantidadReparaciones(reparacion);
        assertThat(descuento).isEqualTo(0.05);
    }

    @Test
    void whenDiaReparacion_thenDescuento() {
        double descuento = pagoService.descuentoDiaAtencion(reparacion);
        assertThat(descuento).isEqualTo(0);
    }

    @Test
    void whenMarca_thenDescuento() {
        this.marca.setCantidadBonos(3);
        this.marca.setDescuento(70000);
        this.marca.setFechaBono(LocalDateTime.now());
        this.marca.setNombre("Chevrolet");
        this.marcaService.guardarMarca(marca);
        double descuento = pagoService.descuentoMarca(reparacion);
        assertThat(descuento).isEqualTo(70000);
    }

    @Test
    void whenKilometraje_thenRecargo() {
        double descuento = pagoService.recargoKilometraje(reparacion);
        assertThat(descuento).isEqualTo(0.2);
    }

    @Test
    void whenAntiguedad_thenRecargo() {
        double descuento = pagoService.recargoAntiguedadVehiculo(reparacion);
        assertThat(descuento).isEqualTo(0.15);
    }

    @Test
    void whenDiasDesdeSalida_thenRecargo() {
        double descuento = pagoService.recargoDiasDesdeSalida(reparacion);
        assertThat(descuento).isEqualTo(0.1);
    }


    @Test
    void whenDescuentos_thenDescuentos() {
        double descuento = pagoService.descuentos(reparacion);
        assertThat(descuento).isEqualTo(0.2);
    }

    @Test
    void whenRecargos_thenRecargos() {
        double recargo = pagoService.recargos(reparacion);
        assertThat(recargo).isCloseTo(recargo, Percentage.withPercentage(.001));
    }

    @Test
    void whenTotalPagar_thenTotalPagar() {
        double total = pagoService.totalPagar(reparacion);
        assertThat(total).isEqualTo(242500);
    }

    @Test
      void whenPreciosPorMotor_thenPrecio() {
         double precioReparacionVSMotor = pagoService.preciosMotor[0][0];
         assertThat(precioReparacionVSMotor).isEqualTo(120000);
      }

   
      @Test
      void whenDescuentosCantidadReparaciones_thenDescuento() {
         double descuento = pagoService.descuentosMotor[0][0];
         assertThat(descuento).isEqualTo(0.05);
      }

      @Test
      void whenDescuentosDiaAtencion_thenDescuento() {
         double descuento = pagoService.recargoKilometraje[0][0]; 
         assertThat(descuento).isEqualTo(0);
      }

      @Test
      void whenDescuentosMarca_thenDescuento() {
         double descuento = pagoService.recargoAntiguedad[3][0];
         assertThat(descuento).isEqualTo(0.15);
      }

      @Test
      void whenNumeroMotor_thenNumber() {
         int recargo = pagoService.numeroMotor(reparacion);
         assertThat(recargo).isEqualTo(0);
      }

      @Test
      void whenNumeroTipoVehiculo_thenNumber() {
         int recargo = pagoService.numeroTipoVehiculo(reparacion);
         assertThat(recargo).isEqualTo(0);
      }

      @Test
      void whenMotor_thenString() {
         String recargo = pagoService.motores[0];
         assertThat(recargo).isEqualTo("Gasolina");
      }

      @Test
      void whenTipoAuto_thenString() {
         String recargo = pagoService.tipoAuto[0];
         assertThat(recargo).isEqualTo("Sedán");
      }

      
          

}