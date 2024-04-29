package tingeso.autofix.controllers;

import tingeso.autofix.entities.MarcaEntity;
import tingeso.autofix.services.MarcaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MarcaController.class)
public class MarcaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarcaService marcaService;


    @Test
    public void listMarcas_ShouldReturnMarcas() throws Exception {

        MarcaEntity marca1 = new MarcaEntity();
        marca1.setCantidadBonos(3);
        marca1.setDescuento(70000);
        marca1.setFechaBono(LocalDateTime.now());
        marca1.setNombre("Toyota");

        // Crear el segundo objeto MarcaEntity para probar
        MarcaEntity marca2 = new MarcaEntity();
        marca2.setCantidadBonos(2);
        marca2.setDescuento(50000);
        marca2.setFechaBono(LocalDateTime.now());
        marca2.setNombre("Kia");

        List<MarcaEntity> marcaList = new ArrayList<>(Arrays.asList(marca1, marca2));

        given(marcaService.obtenerMarcas()).willReturn((ArrayList<MarcaEntity>) marcaList);

        mockMvc.perform(get("/api/v1/marcas/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Toyota")))
                .andExpect(jsonPath("$[1].nombre", is("Kia")));
    }

    @Test
    public void getMarcaById_ShouldReturnMarca() throws Exception {
        MarcaEntity marca = new MarcaEntity();
        marca.setCantidadBonos(3);
        marca.setDescuento(70000);
        marca.setFechaBono(LocalDateTime.now());
        marca.setNombre("Toyota");

        given(marcaService.findById(1L)).willReturn(marca);

        mockMvc.perform(get("/api/v1/marcas/marca/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre", is("Toyota")));
    }

    @Test
    public void saveMarca_ShouldReturnSavedMarca() throws Exception {
        MarcaEntity savedMarca = new MarcaEntity();
        savedMarca.setCantidadBonos(3);
        savedMarca.setDescuento(70000);
        savedMarca.setFechaBono(LocalDateTime.now());
        savedMarca.setNombre("Toyota");

        given(marcaService.guardarMarca(Mockito.any(MarcaEntity.class))).willReturn(savedMarca);

        String marcaJson = """
            {
                "cantidadBonos": 3,
                "descuento": 70000,
                "fechaBono": "2021-06-01T12:00:00",
                "nombre": "Toyota"
            }
            """;

        mockMvc.perform(post("/api/v1/marcas/marca")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(marcaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Toyota")));
    }

    @Test
    public void updateMarca_ShouldReturnUpdatedMarca() throws Exception {
        MarcaEntity updatedMarca = new MarcaEntity();
        updatedMarca.setCantidadBonos(3);
        updatedMarca.setDescuento(70000);
        updatedMarca.setFechaBono(LocalDateTime.now());
        updatedMarca.setNombre("Kia");

        given(marcaService.updateMarca(Mockito.any(MarcaEntity.class))).willReturn(updatedMarca);

        String marcaJson = """
            {
                "cantidadBonos": 3,
                "descuento": 70000,
                "fechaBono": "2021-06-01T12:00:00",
                "nombre": "Kia"
            }
            """;


        mockMvc.perform(put("/api/v1/marcas/marca")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(marcaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Kia")));
    }

    @Test
    public void deleteMarcaById_ShouldReturn204() throws Exception {

        MarcaEntity savedMarca = new MarcaEntity();
        savedMarca.setCantidadBonos(3);
        savedMarca.setDescuento(70000);
        savedMarca.setFechaBono(LocalDateTime.now());
        savedMarca.setNombre("Toyota");

        when(marcaService.deleteMarca(1L)).thenReturn(true); // Assuming the method returns a boolean

        mockMvc.perform(delete("/api/v1/marcas/marca/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}