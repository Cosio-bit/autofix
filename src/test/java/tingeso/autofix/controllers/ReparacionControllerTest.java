package tingeso.autofix.controllers;

import org.junit.jupiter.api.BeforeAll;
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

    
    @BeforeEach
    void setUp() {
        VehiculoEntity vehiculo = new VehiculoEntity();
        vehiculo.setPatente("GHI789");
        vehiculo.setMarca("Chevrolet");
        vehiculo.setModelo("Cruze");
        vehiculo.setAnnoFabricacion("2019");
        vehiculo.setTipoVehiculo("Sedan");
        vehiculo.setTipoMotor("Gasolina");
        vehiculo.setNroAsientos(5);
        vehiculo.setKilometraje(20000);

        vehiculoService.guardarVehiculo(vehiculo);
    }


    @Test
    public void listReparacions_ShouldReturnReparacions() throws Exception {

        ReparacionEntity reparacion1 = new ReparacionEntity();
        reparacion1.setFechaHoraIngreso(LocalDateTime.now());
        reparacion1.setFechaHoraSalida(null);
        reparacion1.setFechaHoraRetiro(null);
        reparacion1.setMontoTotal(null);
        reparacion1.setTipoReparacion("1");
        reparacion1.setIdVehiculo("1");

        // Crear el segundo objeto ReparacionEntity para probar
        ReparacionEntity reparacion2 = new ReparacionEntity();
        reparacion2.setFechaHoraIngreso(LocalDateTime.now());
        reparacion2.setFechaHoraSalida(null);
        reparacion2.setFechaHoraRetiro(null);
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

    @Test
    public void getReparacionById_ShouldReturnReparacion() throws Exception {
        ReparacionEntity reparacion = new ReparacionEntity();
        reparacion.setId(1L);
        reparacion.setFechaHoraIngreso(LocalDateTime.now());
        reparacion.setFechaHoraSalida(null);
        reparacion.setFechaHoraRetiro(null);
        reparacion.setMontoTotal(null);
        reparacion.setTipoReparacion("1");
        reparacion.setIdVehiculo("1");

        reparacionService.guardarReparacion(reparacion);

        given(reparacionService.findById(1L)).willReturn(reparacion);

        mockMvc.perform(get("/api/v1/reparaciones/reparacion/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void saveReparacion_ShouldReturnSavedReparacion() throws Exception {
        ReparacionEntity reparacion = new ReparacionEntity();
        reparacion.setId(1L);   
        reparacion.setFechaHoraIngreso(LocalDateTime.now());
        reparacion.setFechaHoraSalida(null);
        reparacion.setFechaHoraRetiro(null);
        reparacion.setMontoTotal(null);
        reparacion.setTipoReparacion("1");
        reparacion.setIdVehiculo("1");

        given(reparacionService.guardarReparacion(Mockito.any(ReparacionEntity.class))).willReturn(reparacion);

        String reparacionJson = """
            {
                "Id": "1",
                "FechaHoraIngreso": "2021-06-01T00:00:00",
                "FechaHoraSalida": null,
                "FechaHoraRetiro": null,
                "MontoTotal": null,
                "TipoReparacion": "1",
                "IdVehiculo": "1"
            }
            """;

        mockMvc.perform(post("/api/v1/reparaciones/crearReparacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reparacionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void updateReparacion_ShouldReturnUpdatedReparacion() throws Exception {
        ReparacionEntity updatedReparacion = new ReparacionEntity();
        updatedReparacion.setId(1L);
        updatedReparacion.setFechaHoraIngreso(LocalDateTime.now());
        updatedReparacion.setFechaHoraSalida(null);
        updatedReparacion.setFechaHoraRetiro(null);
        updatedReparacion.setMontoTotal(null);
        updatedReparacion.setTipoReparacion("1");
        updatedReparacion.setIdVehiculo("1");

        given(reparacionService.updateReparacion(Mockito.any(ReparacionEntity.class))).willReturn(updatedReparacion);

        String reparacionJson = """
            {
                "Id": "1",
                "FechaHoraIngreso": "2021-06-01T00:00:00",
                "FechaHoraSalida": null,
                "FechaHoraRetiro": null,
                "MontoTotal": null,
                "TipoReparacion": "1",
                "IdVehiculo": "1"
            }
            """;

        mockMvc.perform(put("/api/v1/reparaciones/reparacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reparacionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void deleteReparacionById_ShouldReturn204() throws Exception {

        ReparacionEntity savedReparacion = new ReparacionEntity();
        savedReparacion.setFechaHoraIngreso(LocalDateTime.now());
        savedReparacion.setFechaHoraSalida(null);
        savedReparacion.setFechaHoraRetiro(null);
        savedReparacion.setMontoTotal(null);
        savedReparacion.setTipoReparacion("1");
        savedReparacion.setIdVehiculo("1");

        when(reparacionService.deleteReparacion(1L)).thenReturn(true); // Assuming the method returns a boolean

        mockMvc.perform(delete("/api/v1/reparaciones/reparacion/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}