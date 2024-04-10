package tingeso.autofix.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.autofix.entities.VehiculoEntity;
import tingeso.autofix.repositories.VehiculoRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class VehiculoService {
    @Autowired
    VehiculoRepository vehiculoRepository;

    public ArrayList<VehiculoEntity> obtenerVehiculos() {
        return (ArrayList<VehiculoEntity>) vehiculoRepository.findAll();
    }

    public VehiculoEntity guardarVehiculo(VehiculoEntity vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public VehiculoEntity obtenerPorId(Long id) {
        return vehiculoRepository.findById(id).get();
    }

    public Optional<VehiculoEntity> obtenerPorPatente(String patente) {
        return vehiculoRepository.findByPatente(patente);
    }

    public boolean deleteVehiculo(Long id) throws Exception {
        try {
            vehiculoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public VehiculoEntity updateVehiculo(VehiculoEntity vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

}