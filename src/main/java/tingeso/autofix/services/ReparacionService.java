package tingeso.autofix.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.autofix.entities.ReparacionEntity;
import tingeso.autofix.repositories.ReparacionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReparacionService {
    @Autowired
    ReparacionRepository reparacionRepository;
    @Autowired
    VehiculoService vehiculoService;

    public ArrayList<ReparacionEntity> obtenerReparaciones(){
        return (ArrayList<ReparacionEntity>) reparacionRepository.findAll();
    }

    public ArrayList<ReparacionEntity> obtenerReparacionesPorIdVehiculo(String idVehiculo) {
        return (ArrayList<ReparacionEntity>)  reparacionRepository.findByVehiculoID(idVehiculo);
    }

    public ReparacionEntity guardarReparacion(ReparacionEntity reparacion){
        if (reparacion.getFechaHoraIngreso() == null) {
            reparacion.setFechaHoraSalida(LocalDateTime.now());
        }
        reparacionRepository.save(reparacion);
        return reparacion;
    }

    public ReparacionEntity updateReparacionSalida(ReparacionEntity reparacion){
        if (reparacion.getFechaHoraSalida() == null) {
            reparacion.setFechaHoraSalida(LocalDateTime.now());
            reparacionRepository.save(reparacion);
        }
        return reparacion;
    }

    public ReparacionEntity updateReparacionRetiro(ReparacionEntity reparacion){
        if (reparacion.getFechaHoraRetiro() == null && reparacion.getFechaHoraSalida() != null) {
            reparacion.setFechaHoraSalida(LocalDateTime.now());
            reparacionRepository.save(reparacion);
        }
        return reparacion;
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
        int montoReparacion = tipoReparacionVStipoMotor(reparacion); //reparaciones, se calcula 1 vez
        recargos(reparacion);
        descuentos(reparacion);
        return reparacion;
    }
    public int tipoReparacionVStipoMotor(ReparacionEntity reparacion) {
        // Definir una matriz de precios donde las filas representan los tipos de reparaciones
        // y las columnas representan los tipos de motor.
        int[][] precios = {
                // tipo de motor 1, 2, 3, 4, 5 y 6 respectivamente
                {10, 20, 30, 40, 50, 60}, // reparación tipo 1
                {15, 25, 35, 45, 55, 65}, // reparación tipo 2
                {12, 22, 32, 42, 52, 62}, // reparación tipo 3
                {12, 22, 32, 42, 52, 62}, // reparación tipo 4
                {12, 22, 32, 42, 52, 62}, // reparación tipo 5
                {12, 22, 32, 42, 52, 62}, // reparación tipo 6
                {12, 22, 32, 42, 52, 62}, // reparación tipo 7
                {12, 22, 32, 42, 52, 62}, // reparación tipo 8
                {12, 22, 32, 42, 52, 62}, // reparación tipo 9
                {12, 22, 32, 42, 52, 62}, // reparación tipo 10
                {12, 22, 32, 42, 52, 62}, // reparación tipo 11

                // Puedes continuar con los precios para las otras reparaciones...
        };

        // Obtener el tipo de reparación y el tipo de motor del vehículo
        String stringTipoReparacion = reparacion.getTipoReparacion();
        List<Integer> tipoReparacion = new ArrayList<>();

        // Step 1: Split the string using comma as delimiter
        String[] parts = stringTipoReparacion.split(",");

        // Step 2 & 3: Convert substrings to integers and add to list
        for (String part : parts) {
            int num = Integer.parseInt(part.trim()); // trim to remove leading/trailing whitespaces
            tipoReparacion.add(num);
        }

        int tipoMotor = Integer.parseInt(vehiculoService.obtenerPorId(Long.valueOf(reparacion.getIdVehiculo())).getTipoMotor());

        // Verificar que los tipos de reparación y de motor estén dentro de los rangos adecuados
        if (tipoReparacion.size() >= 1 && tipoReparacion.size() <= 11 && tipoMotor >= 1 && tipoMotor <= 6) {
            // Obtener el precio correspondiente de la matriz de precios
            int precio = precios[tipoReparacion.get(0) - 1][tipoMotor - 1];
            return precio;
        } else {
            // Si los tipos están fuera de rango, devolver un valor predeterminado o lanzar una excepción según sea necesario
            return -1; // O puedes lanzar una excepción indicando que los tipos son inválidos
        }
    }



    public int descuentos(ReparacionEntity reparacion){

        int descuento = 0;
        /*descuentoCantidadReparaciones(reparacion);
        descuentoDiaAtencion(reparacion);
        descuentoBono(reparacion);*/
        return descuento;
    }
    public int recargos(ReparacionEntity reparacion){
        int recargo = 0;
        /*recargoKilometraje(reparacion);
        recargoAntiguedadVehiculo(reparacion);
        recargoPorRetrasoRetiro(reparacion);*/
        return recargo;
    }


    public int calcularAtrazo(LocalDate fechaActual, LocalDate fechaVencimiento) {
        // Calcula el período entre la fecha del último pago y la fecha actual
        Period periodo = Period.between(fechaVencimiento, fechaActual);

        // Calcula el total de meses de atraso
        int aniosDiferencia = periodo.getYears();
        int mesesDiferencia = periodo.getMonths();

        return aniosDiferencia * 12 + mesesDiferencia;
    }

}