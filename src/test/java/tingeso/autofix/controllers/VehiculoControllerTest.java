package tingeso.autofix.controllers;

import tingeso.autofix.entities.VehiculoEntity;
import tingeso.autofix.services.VehiculoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehiculoController.class)
public class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehiculoService vehiculoService;


    @Test
    public void listVehiculos_ShouldReturnVehiculos() throws Exception {

        VehiculoEntity vehiculo1 = new VehiculoEntity();
        vehiculo1.setPatente("GHI789");
        vehiculo1.setMarca("Chevrolet");
        vehiculo1.setModelo("Cruze");
        vehiculo1.setAnnoFabricacion("2019");
        vehiculo1.setTipoVehiculo("Sedan");
        vehiculo1.setTipoMotor("Gasolina");
        vehiculo1.setNroAsientos(5);
        vehiculo1.setKilometraje(20000);

        // Crear el segundo objeto VehiculoEntity para probar
        VehiculoEntity vehiculo2 = new VehiculoEntity();
        vehiculo2.setPatente("ABC123");
        vehiculo2.setMarca("Toyota");
        vehiculo2.setModelo("Corolla");
        vehiculo2.setAnnoFabricacion("2020");
        vehiculo2.setTipoVehiculo("Sedan");
        vehiculo2.setTipoMotor("Híbrido");
        vehiculo2.setNroAsientos(5);
        vehiculo2.setKilometraje(15000);

        List<VehiculoEntity> vehiculoList = new ArrayList<>(Arrays.asList(vehiculo1, vehiculo2));

        given(vehiculoService.obtenerVehiculos()).willReturn((ArrayList<VehiculoEntity>) vehiculoList);

        mockMvc.perform(get("/api/v1/vehiculos/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].patente", is("GHI789")))
                .andExpect(jsonPath("$[1].patente", is("ABC123")));
    }

    @Test
    public void getVehiculoById_ShouldReturnVehiculo() throws Exception {
        VehiculoEntity vehiculo = new VehiculoEntity();
        vehiculo.setPatente("GHI789");
        vehiculo.setMarca("Chevrolet");
        vehiculo.setModelo("Cruze");
        vehiculo.setAnnoFabricacion("2019");
        vehiculo.setTipoVehiculo("Sedan");
        vehiculo.setTipoMotor("Gasolina");
        vehiculo.setNroAsientos(5);
        vehiculo.setKilometraje(20000);

        given(vehiculoService.obtenerPorId(1L)).willReturn(vehiculo);

        mockMvc.perform(get("/api/v1/vehiculos/vehiculo/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patente", is("GHI789")));
    }

    @Test
    public void saveVehiculo_ShouldReturnSavedVehiculo() throws Exception {
        VehiculoEntity savedVehiculo = new VehiculoEntity();
        savedVehiculo.setPatente("GHI789");
        savedVehiculo.setMarca("Chevrolet");
        savedVehiculo.setModelo("Cruze");
        savedVehiculo.setAnnoFabricacion("2019");
        savedVehiculo.setTipoVehiculo("Sedan");
        savedVehiculo.setTipoMotor("Gasolina");
        savedVehiculo.setNroAsientos(5);
        savedVehiculo.setKilometraje(20000);

        given(vehiculoService.guardarVehiculo(Mockito.any(VehiculoEntity.class))).willReturn(savedVehiculo);

        String vehiculoJson = """
            {
                "patente": "GHI789",
                "marca": "Chevrolet",
                "modelo": "Cruze",
                "annoFabricacion": "2019",
                "tipoVehiculo": "Sedan",
                "tipoMotor": "Gasolina",
                "nroAsientos": 5,
                "kilometraje": 20000
            }
            """;

        mockMvc.perform(post("/api/v1/vehiculos/vehiculo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehiculoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patente", is("GHI789")));
    }

    @Test
    public void updateVehiculo_ShouldReturnUpdatedVehiculo() throws Exception {
        VehiculoEntity updatedVehiculo = new VehiculoEntity();
        updatedVehiculo.setPatente("GHI789");
        updatedVehiculo.setMarca("Chevrolet");
        updatedVehiculo.setModelo("Cruze");
        updatedVehiculo.setAnnoFabricacion("2019");
        updatedVehiculo.setTipoVehiculo("Sedan");
        updatedVehiculo.setTipoMotor("Gasolina");
        updatedVehiculo.setNroAsientos(5);
        updatedVehiculo.setKilometraje(20000);

        given(vehiculoService.updateVehiculo(Mockito.any(VehiculoEntity.class))).willReturn(updatedVehiculo);

        String vehiculoJson = """
            {
                "patente": "GHI789",
                "marca": "Chevrolet",
                "modelo": "Cruze",
                "annoFabricacion": "2019",
                "tipoVehiculo": "Sedan",
                "tipoMotor": "Gasolina",
                "nroAsientos": 5,
                "kilometraje": 20000
            }
            """;


        mockMvc.perform(put("/api/v1/vehiculos/vehiculo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehiculoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patente", is("GHI789")));
    }

    @Test
    public void deleteVehiculoById_ShouldReturn204() throws Exception {

        VehiculoEntity savedVehiculo = new VehiculoEntity();
        savedVehiculo.setPatente("GHI789");
        savedVehiculo.setMarca("Chevrolet");
        savedVehiculo.setModelo("Cruze");
        savedVehiculo.setAnnoFabricacion("2019");
        savedVehiculo.setTipoVehiculo("Sedan");
        savedVehiculo.setTipoMotor("Gasolina");
        savedVehiculo.setNroAsientos(5);
        savedVehiculo.setKilometraje(20000);

        when(vehiculoService.deleteVehiculo(1L)).thenReturn(true); // Assuming the method returns a boolean

        mockMvc.perform(delete("/api/v1/vehiculos/vehiculo/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}