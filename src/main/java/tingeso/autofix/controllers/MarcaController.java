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
@CrossOrigin("*")
public class MarcaController {
    
        @Autowired
        private MarcaService marcaService;
    
        @GetMapping("/")
        public List<MarcaEntity> listar() {
            return marcaService.obtenerMarcas();
        }
    
        @GetMapping("/{id}")
        public Optional<MarcaEntity> mostrarMarca(@PathVariable Long id) {
            Optional<MarcaEntity> marca = Optional.ofNullable(marcaService.findById(id));
            if (marca.isPresent()) {
                MarcaEntity marcaEntity = marca.get();
                return Optional.of(marcaEntity);
            }
            return Optional.empty();
        }
    
        @PostMapping("/crearMarca")
        public MarcaEntity nuevaMarca(
                @RequestBody MarcaEntity marca) {
            return marcaService.guardarMarca(marca);
        }
    
        @GetMapping("/crearMarca")
        public MarcaEntity MarcaForm() {
            return new MarcaEntity();
        }
    
        @PutMapping("/marca")
        public ResponseEntity<MarcaEntity> updateMarca(@RequestBody MarcaEntity marca){
            MarcaEntity marcaEntity = marcaService.updateMarca(marca);
            return ResponseEntity.ok(marcaEntity);
        }
    
        @DeleteMapping("/marca/{id}")
        public ResponseEntity<Boolean> deleteMarcaById(@PathVariable Long id) throws Exception {
            var isDeleted = marcaService.deleteMarca(id);
            return ResponseEntity.noContent().build();
        }

        @PostMapping("/uploadFile")
        public String handleFileUpload(@RequestParam("file") MultipartFile file) {
            if (file.isEmpty()) {
                return "Please select a file to upload";
            }
            try {
                //get the name of the file
                String fileName = file.getOriginalFilename();
                marcaService.processFile(file, fileName);
                return "File uploaded successfully!";
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to upload file";
            }
        }
    
    
}
