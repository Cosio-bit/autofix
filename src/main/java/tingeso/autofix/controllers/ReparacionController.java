package tingeso.autofix.controllers;


import org.springframework.beans.factory.annotation.Autowired;
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
        if (reparacion.isPresent()) {
            ReparacionEntity reparacionEntity = reparacion.get();
            return Optional.of(reparacionEntity);
        }
        return Optional.empty();
    }

    @PostMapping("/crearReparacion/{idVehiculo}")
    public ReparacionEntity guardarReparacion(
            @RequestBody ReparacionEntity reparacion) {
        return reparacionService.guardarReparacion(reparacion);
    }

    @GetMapping("/crearReparacion/{idVehiculo}")
    public ReparacionEntity VehiculoForm(@PathVariable String idVehiculo) {
        return new ReparacionEntity();
    }


    @PutMapping("/reparacion/{reparacionId}/salida")
    public ReparacionEntity updateSalidaDateAndTime(@PathVariable Long reparacionId) {
        ReparacionEntity reparacion = reparacionService.findById(reparacionId);
        return reparacionService.updateReparacionSalida(reparacion);
    }

    @PutMapping("/reparacion/{reparacionId}/retiro")
    public ReparacionEntity updateRetiroDateAndTime(@PathVariable Long reparacionId) {
        ReparacionEntity reparacion = reparacionService.findById(reparacionId);
        return reparacionService.updateReparacionRetiro(reparacion);
    }

    @PutMapping("/reparacion/{reparacionId}/monto")
    public ReparacionEntity updateMonto(@PathVariable Long reparacionId) {
        ReparacionEntity reparacion = reparacionService.findById(reparacionId);
        return reparacionService.updateMontoTotal(reparacion);
    }

}





