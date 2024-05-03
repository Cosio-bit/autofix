package tingeso.autofix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.autofix.entities.ReparacionEntity;
import tingeso.autofix.entities.VehiculoEntity;
import tingeso.autofix.services.ReparacionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reparaciones")
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

    //obtener todas las reparaciones de una marca de vehiculo
    @GetMapping("/marca/{marca}")
    public List<ReparacionEntity> mostrarReparacionesPorMarca(@PathVariable("marca") String marca) {
        return reparacionService.obtenerReparacionesPorMarca(marca);
    }

    //obtener todas las reparaciones de un tipo de vehiculo
    @GetMapping("/tipoVehiculo/{tipoVehiculo}")
    public List<ReparacionEntity> mostrarReparacionesPorTipoVehiculo(@PathVariable("tipoVehiculo") String tipoVehiculo) {
        return reparacionService.obtenerReparacionesPorTipoVehiculo(tipoVehiculo);
    }

    //obtener todas las reparaciones de un tipo de motor
    @GetMapping("/tipoMotor/{tipoMotor}")
    public List<ReparacionEntity> mostrarReparacionesPorTipoMotor(@PathVariable("tipoMotor") String tipoMotor) {
        return reparacionService.obtenerReparacionesPorTipoMotor(tipoMotor);
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
    public ResponseEntity<String> updateMonto(@RequestBody ReparacionEntity reparacion) {
        // Update the total amount
        reparacionService.updateMontoTotal(reparacion).getLeft();

        // Custom response map containing both the updated entity and a separate string
        String additionalString = reparacionService.updateMontoTotal(reparacion).getRight();

        return ResponseEntity.ok(additionalString);
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





