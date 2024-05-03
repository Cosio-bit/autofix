package tingeso.autofix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.autofix.entities.MarcaEntity;
import tingeso.autofix.services.MarcaService;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping("/")
    public List<MarcaEntity> listar() {
        return marcaService.obtenerMarcas();
    }

    @GetMapping("/marca/{id}")
    public Optional<MarcaEntity> mostrarMarca(@PathVariable Long id) {
        Optional<MarcaEntity> marca = Optional.ofNullable(marcaService.findById(id));
        MarcaEntity marcaEntity = marca.get();
        return Optional.of(marcaEntity);
    }

    @PostMapping("/marca")
    public MarcaEntity nuevaMarca(
            @RequestBody MarcaEntity marca) {
        return marcaService.guardarMarca(marca);
    }

    @GetMapping("/marca")
    public MarcaEntity MarcaForm() {
        return new MarcaEntity();
    }

    @PutMapping("/marca")
    public ResponseEntity<MarcaEntity> updateMarca(@RequestBody MarcaEntity marca) {
        MarcaEntity marcaEntity = marcaService.updateMarca(marca);
        return ResponseEntity.ok(marcaEntity);
    }

    @DeleteMapping("/marca/{id}")
    public ResponseEntity<Boolean> deleteMarcaById(@PathVariable Long id) throws Exception {
        var isDeleted = marcaService.deleteMarca(id);
        return ResponseEntity.noContent().build();
    }
}
