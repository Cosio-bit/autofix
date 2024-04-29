package tingeso.autofix.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tingeso.autofix.entities.ReparacionEntity;
import tingeso.autofix.entities.VehiculoEntity;
import tingeso.autofix.services.ReparacionService;
import tingeso.autofix.services.VehiculoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReparacionController.class)
public class ReparacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReparacionService reparacionService;

    @MockBean
    private VehiculoService vehiculoService;

    //create a vehiculo for each test
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
    }


    @Test
    public void listReparacions_ShouldReturnReparacions() throws Exception {


        ReparacionEntity reparacion1 = new ReparacionEntity();
        reparacion1.setFechaHoraIngreso(LocalDateTime.now());
        reparacion1.setFechaHoraSalida(null);
        reparacion1.setMontoTotal(null);
        reparacion1.setTipoReparacion("1");
        reparacion1.setIdVehiculo("1");

        // Crear el segundo objeto ReparacionEntity para probar
        ReparacionEntity reparacion2 = new ReparacionEntity();
        reparacion2.setFechaHoraIngreso(LocalDateTime.now());
        reparacion2.setFechaHoraSalida(null);
        reparacion2.setMontoTotal(null);
        reparacion2.setTipoReparacion("2");
        reparacion2.setIdVehiculo("1");


        List<ReparacionEntity> reparacionList = new ArrayList<>(Arrays.asList(reparacion1, reparacion2));

        given(reparacionService.obtenerReparaciones()).willReturn((ArrayList<ReparacionEntity>) reparacionList);

        mockMvc.perform(get("/api/v1/reparaciones/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tipoReparacion", is("1")))
                .andExpect(jsonPath("$[1].tipoReparacion", is("2")));
    }
/* 
    @Test
    public void getReparacionById_ShouldReturnReparacion() throws Exception {
        ReparacionEntity reparacion = new ReparacionEntity();
        reparacion.setPatente("GHI789");
        reparacion.setMarca("Chevrolet");
        reparacion.setModelo("Cruze");
        reparacion.setAnnoFabricacion("2019");
        reparacion.setTipoReparacion("Sedan");
        reparacion.setTipoMotor("Gasolina");
        reparacion.setNroAsientos(5);
        reparacion.setKilometraje(20000);

        given(reparacionService.obtenerPorId(1L)).willReturn(reparacion);

        mockMvc.perform(get("/api/v1/reparacions/reparacion/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patente", is("GHI789")));
    }

    @Test
    public void saveReparacion_ShouldReturnSavedReparacion() throws Exception {
        ReparacionEntity savedReparacion = new ReparacionEntity();
        savedReparacion.setPatente("GHI789");
        savedReparacion.setMarca("Chevrolet");
        savedReparacion.setModelo("Cruze");
        savedReparacion.setAnnoFabricacion("2019");
        savedReparacion.setTipoReparacion("Sedan");
        savedReparacion.setTipoMotor("Gasolina");
        savedReparacion.setNroAsientos(5);
        savedReparacion.setKilometraje(20000);

        given(reparacionService.guardarReparacion(Mockito.any(ReparacionEntity.class))).willReturn(savedReparacion);

        String reparacionJson = """
            {
                "patente": "GHI789",
                "marca": "Chevrolet",
                "modelo": "Cruze",
                "annoFabricacion": "2019",
                "tipoReparacion": "Sedan",
                "tipoMotor": "Gasolina",
                "nroAsientos": 5,
                "kilometraje": 20000
            }
            """;

        mockMvc.perform(post("/api/v1/reparacions/reparacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reparacionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patente", is("GHI789")));
    }

    @Test
    public void updateReparacion_ShouldReturnUpdatedReparacion() throws Exception {
        ReparacionEntity updatedReparacion = new ReparacionEntity();
        updatedReparacion.setPatente("GHI789");
        updatedReparacion.setMarca("Chevrolet");
        updatedReparacion.setModelo("Cruze");
        updatedReparacion.setAnnoFabricacion("2019");
        updatedReparacion.setTipoReparacion("Sedan");
        updatedReparacion.setTipoMotor("Gasolina");
        updatedReparacion.setNroAsientos(5);
        updatedReparacion.setKilometraje(20000);

        given(reparacionService.updateReparacion(Mockito.any(ReparacionEntity.class))).willReturn(updatedReparacion);

        String reparacionJson = """
            {
                "patente": "GHI789",
                "marca": "Chevrolet",
                "modelo": "Cruze",
                "annoFabricacion": "2019",
                "tipoReparacion": "Sedan",
                "tipoMotor": "Gasolina",
                "nroAsientos": 5,
                "kilometraje": 20000
            }
            """;


        mockMvc.perform(put("/api/v1/reparacions/reparacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reparacionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patente", is("GHI789")));
    }

    @Test
    public void deleteReparacionById_ShouldReturn204() throws Exception {

        ReparacionEntity savedReparacion = new ReparacionEntity();
        savedReparacion.setPatente("GHI789");
        savedReparacion.setMarca("Chevrolet");
        savedReparacion.setModelo("Cruze");
        savedReparacion.setAnnoFabricacion("2019");
        savedReparacion.setTipoReparacion("Sedan");
        savedReparacion.setTipoMotor("Gasolina");
        savedReparacion.setNroAsientos(5);
        savedReparacion.setKilometraje(20000);

        when(reparacionService.deleteReparacion(1L)).thenReturn(true); // Assuming the method returns a boolean

        mockMvc.perform(delete("/api/v1/reparacions/reparacion/{id}", 1L))
                .andExpect(status().isNoContent());
    }*/
}