package tingeso.autofix.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.autofix.entities.VehiculoEntity;
import tingeso.autofix.services.VehiculoService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/vehiculos")
@CrossOrigin("*")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/vehiculo/{id}")
    public Optional<VehiculoEntity> mostrarVehiculo(@PathVariable Long id) {
        Optional<VehiculoEntity> vehiculo = Optional.ofNullable(vehiculoService.obtenerPorId(id));
        if (vehiculo.isPresent()) {
            VehiculoEntity vehiculoEntity = vehiculo.get();
            return Optional.of(vehiculoEntity);
        }
        return Optional.empty();
    }

    @GetMapping("/vehiculo")
    public VehiculoEntity VehiculoFormvehiculo() {
        return new VehiculoEntity();
    }

	@PostMapping("/vehiculo")
	public VehiculoEntity nuevoVehiculo(@RequestBody VehiculoEntity vehiculo) {
		return vehiculoService.guardarVehiculo(vehiculo);
	}

	@GetMapping("/")
	public List<VehiculoEntity> listar() {
		return vehiculoService.obtenerVehiculos();
	}

    @DeleteMapping("/vehiculo/{id}")
    public ResponseEntity<Boolean> deleteVehiculoById(@PathVariable Long id) throws Exception {
        var isDeleted = vehiculoService.deleteVehiculo(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/vehiculo")
    public ResponseEntity<VehiculoEntity> updateVehiculo(@RequestBody VehiculoEntity vehiculo){
        VehiculoEntity vehiculo1 = vehiculoService.updateVehiculo(vehiculo);
        return ResponseEntity.ok(vehiculo1);
    }


}


