package tingeso.autofix.services;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.autofix.entities.ReparacionEntity;
import tingeso.autofix.entities.VehiculoEntity;
import tingeso.autofix.repositories.ReparacionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.Comparator;

@Service
public class ReparacionService {
    @Autowired
    ReparacionRepository reparacionRepository;
    @Autowired
    VehiculoService vehiculoService;
    @Autowired
    PagoService pagoService;

    public ArrayList<ReparacionEntity> obtenerReparaciones(){
        return (ArrayList<ReparacionEntity>) reparacionRepository.findAll();
    }

    public ArrayList<ReparacionEntity> obtenerReparacionesPorIdVehiculo(String idVehiculo) {
        return (ArrayList<ReparacionEntity>)  reparacionRepository.findByVehiculoID(idVehiculo);
    }

    public ReparacionEntity guardarReparacion(ReparacionEntity reparacion){
        return reparacionRepository.save(reparacion);
    }

    public ReparacionEntity updateReparacion(ReparacionEntity reparacion) {
        return reparacionRepository.save(reparacion);
    }
    public boolean deleteReparacion(Long id) throws Exception {
        try {
            reparacionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public ReparacionEntity findById(Long reparacionId) {
        Optional<ReparacionEntity> optionalReparacion = reparacionRepository.findById(reparacionId);
        return optionalReparacion.orElse(null);
    }

    public Pair<ReparacionEntity, String> updateMontoTotal(ReparacionEntity reparacion){
        reparacion.setMontoTotal(pagoService.calcularPago(reparacion).getFirst());
        String stringInfo = String.valueOf(pagoService.calcularPago(reparacion).getSecond());

        reparacionRepository.save(reparacion);
        return Pair.of(reparacion, stringInfo);
    }


    // Función para obtener reparaciones por marca de vehículo
    public List<ReparacionEntity> obtenerReparacionesPorMarca(String marca) {
        List<VehiculoEntity> vehiculos = vehiculoService.obtenerVehiculosPorMarca(marca);
        //print vehiculos in console
        System.out.println(vehiculos);
        System.out.println("marca: " + marca);
        List<ReparacionEntity> reparaciones = new ArrayList<>();
        for (VehiculoEntity vehiculo : vehiculos) {
            reparaciones.addAll(reparacionRepository.findByVehiculoID(vehiculo.getId().toString()));
        }
    
        // Ordenar reparaciones por duración neta y manejar reparaciones sin fechaHoraSalida
        reparaciones.sort(new Comparator<ReparacionEntity>() {
            public int compare(ReparacionEntity reparacion1, ReparacionEntity reparacion2) {
                // Calcular duración neta (salida - ingreso) para ambas reparaciones
                long duracionNeta1 = getDuracionNeta(reparacion1);
                long duracionNeta2 = getDuracionNeta(reparacion2);

                // Comparar por duración neta
                return Long.compare(duracionNeta1, duracionNeta2);
            }

            // Función para calcular la duración neta de una reparación
            private long getDuracionNeta(ReparacionEntity reparacion) {
                if (reparacion.getFechaHoraSalida() != null) {
                    return java.time.Duration.between(reparacion.getFechaHoraIngreso(), reparacion.getFechaHoraSalida()).toMillis();
                } else {
                    // Si la fechaHoraSalida es null, colocar al final de la lista
                    return Long.MAX_VALUE;
                }
            }
        });
        return reparaciones;
    }

    // Función para obtener reparaciones por tipo de motor de vehículo
    public List<ReparacionEntity> obtenerReparacionesPorTipoMotor(String tipoMotor) {
        List<VehiculoEntity> vehiculos = vehiculoService.obtenerVehiculosPorTipoMotor(tipoMotor);
        List<ReparacionEntity> reparaciones = new ArrayList<>();
        for (VehiculoEntity vehiculo : vehiculos) {
            reparaciones.addAll(reparacionRepository.findByVehiculoID(vehiculo.getId().toString()));
        }
        return reparaciones;
    }

    // Función para obtener reparaciones por tipo de vehículo
    public List<ReparacionEntity> obtenerReparacionesPorTipoVehiculo(String tipoVehiculo) {
        List<VehiculoEntity> vehiculos = vehiculoService.obtenerVehiculosPorTipoVehiculo(tipoVehiculo);
        List<ReparacionEntity> reparaciones = new ArrayList<>();
        for (VehiculoEntity vehiculo : vehiculos) {
            reparaciones.addAll(reparacionRepository.findByVehiculoID(vehiculo.getId().toString()));
        }
        return reparaciones;
    }
    
}