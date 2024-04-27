package tingeso.autofix.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.autofix.entities.ReparacionEntity;
import tingeso.autofix.repositories.ReparacionRepository;

import java.util.ArrayList;
import java.util.Optional;

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

    public ReparacionEntity updateMontoTotal(ReparacionEntity reparacion){
        reparacion.setMontoTotal(pagoService.totalPagar(reparacion));
        reparacionRepository.save(reparacion);
        return reparacion;
    }




}