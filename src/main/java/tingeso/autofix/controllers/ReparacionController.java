package tingeso.autofix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.autofix.entities.ReparacionEntity;
import tingeso.autofix.services.ReparacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reparaciones")
@CrossOrigin("*")
public class ReparacionController {

    @Autowired
    private ReparacionService reparacionService;


    @GetMapping("/")
    public List<ReparacionEntity>  listar() {
        return reparacionService.obtenerReparaciones();
    }

    @GetMapping("/{id}")
    public List<ReparacionEntity> mostrarReparaciones(@PathVariable("id") String id) {
        return reparacionService.obtenerReparacionesPorIdVehiculo(id);
    }

    @GetMapping("/reparacion/{id}")
    public Optional<ReparacionEntity> mostrarReparacion(@PathVariable Long id) {
        Optional<ReparacionEntity> reparacion = Optional.ofNullable(reparacionService.findById(id));
            ReparacionEntity reparacionEntity = reparacion.get();
            return Optional.of(reparacionEntity);
        }
    @PostMapping("/crearReparacion")
    public ReparacionEntity nuevaReparacion(
            @RequestBody ReparacionEntity reparacion) {
        return reparacionService.guardarReparacion(reparacion);
    }

    @GetMapping("/crearReparacion")
    public ReparacionEntity VehiculoForm() {
        return new ReparacionEntity();
    }

    @PutMapping("/reparacion/monto")
    public ResponseEntity<ReparacionEntity> updateMonto(@RequestBody ReparacionEntity reparacion){
        ReparacionEntity reparacionUpdated = reparacionService.updateMontoTotal(reparacion);
        return ResponseEntity.ok(reparacionUpdated);
    }

    @PutMapping("/reparacion")
    public ResponseEntity<ReparacionEntity> updateReparacion(@RequestBody ReparacionEntity reparacion){
        ReparacionEntity reparacionUpdated = reparacionService.updateReparacion(reparacion);
        return ResponseEntity.ok(reparacionUpdated);
    }

    @DeleteMapping("/reparacion/{reparacionId}")
    public ResponseEntity<Boolean> deleteReparacionById(@PathVariable Long reparacionId) throws Exception {
        var isDeleted = reparacionService.deleteReparacion(reparacionId);
        return ResponseEntity.noContent().build();
    }


}





