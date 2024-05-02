package tingeso.autofix.services;
import tingeso.autofix.entities.MarcaEntity;
import tingeso.autofix.entities.ReparacionEntity;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import tingeso.autofix.repositories.ReparacionRepository;
import tingeso.autofix.repositories.VehiculoRepository;


@Service
public class PagoService {
    @Autowired
    VehiculoRepository vehiculoRepository;

    @Autowired
    ReparacionRepository reparacionRepository;

    @Autowired
    MarcaService marcaService;

    // Definir una matriz de precios donde las filas representan los tipos de reparaciones
// y las columnas representan los tipos de motor.
    double[][] preciosMotor = {
            // tipo de motor 1, 2, 3, 4 respectivamente
            {120000, 120000, 180000, 220000},
            {130000, 130000, 190000, 230000},
            {350000, 450000, 700000, 800000},
            {210000, 210000, 300000, 300000},
            {150000, 150000, 200000, 250000},
            {100000, 120000, 450000, 0},
            {100000, 100000, 100000, 100000},
            {180000, 180000, 210000, 250000},
            {150000, 150000, 180000, 180000},
            {130000, 140000, 220000, 0},
            {80000, 80000, 80000, 80000}

    };

    double[][] descuentosMotor = {
            // tipo de motor 1, 2, 3, 4 respectivamente
            {0.05, 0.07, 0.10, 0.08}, // 1
            {0.05, 0.07, 0.10, 0.08}, // 2
            {0.10, 0.12, 0.15, 0.13}, // 3
            {0.10, 0.12, 0.15, 0.13}, // 4
            {0.10, 0.12, 0.15, 0.13}, // 5
            {0.15, 0.17, 0.20, 0.18}, // 6
            {0.15, 0.17, 0.20, 0.18}, // 7
            {0.15, 0.17, 0.20, 0.18}, // 8
            {0.15, 0.17, 0.20, 0.18}, // 9
            {0.20, 0.22, 0.25, 0.23}  // 10
    };

    double[][] recargoKilometraje = {
            // tipos de autos 1,2,3,4,5 respectivamente
            {0.00, 0.00, 0.00, 0.00, 0.00}, // 0 – 5.000
            {0.03, 0.03, 0.05, 0.05, 0.05}, // 5.001 – 12.000
            {0.07, 0.07, 0.09, 0.09, 0.09}, // 12.001 – 25.000
            {0.12, 0.12, 0.12, 0.12, 0.12}, // 25.001 – 40.000
            {0.20, 0.20, 0.20, 0.20, 0.20}  // 40.000 – más

    };

    double[][] recargoAntiguedad = {
            // tipos de autos 1,2,3,4,5 respectivamente
            {0.00, 0.00, 0.00, 0.00, 0.00}, // 0 – 5
            {0.05, 0.05, 0.07, 0.07, 0.07}, // 6 – 10
            {0.09, 0.09, 0.11, 0.11, 0.11}, // 11 – 15
            {0.15, 0.15, 0.20, 0.20, 0.20}  // 16 – más
    };

    String[] motores = {"Gasolina", "Diésel", "Híbrido", "Eléctrico"};

    String[] tipoAuto = {"Sedán", "Hatchback", "SUV", "Pickup", "Furgoneta"};


    public int numeroMotor(ReparacionEntity reparacion) {
        System.out.println("id: " + reparacion.getIdVehiculo());
        //obtener el tipo de motor del vehiculo
        String motor = vehiculoRepository.findById(Long.valueOf(reparacion.getIdVehiculo())).get().getTipoMotor();
        System.out.println("rep: " + motor);
        int motorNum = 0;
        for (int i = 0; i < motores.length; i++) {
            if (motores[i].equals(motor)) {
                motorNum = i;
                break;
            }
        }
        System.out.println("motnum: " + motorNum);

        return motorNum;
    }


    public int numeroTipoVehiculo(ReparacionEntity reparacion) {
        //obtener el tipo de auto del vehiculo
        String tipo = vehiculoRepository.findById(Long.valueOf(reparacion.getIdVehiculo())).get().getTipoVehiculo();
        int tipoNum = 0;
        for (int i = 0; i < tipoAuto.length; i++) {
            if (tipoAuto[i].equals(tipo)) {
                tipoNum = i;
                break;
            }
        }
        return tipoNum;
    }

    public double precioReparacionVSMotor(ReparacionEntity reparacion) {
        //obtener el tipo de motor del vehiculo
        int motorNum = numeroMotor(reparacion);
        //sea una string separada por comas con los tipos de reparacion, obtener cada tipo de reparacion en un arreglo donde cada vez que se encuentre una coma se separa
        String tipoReparacion = reparacion.getTipoReparacion();
        String[] tipoReparacionArray = tipoReparacion.split(",");

        int montoTotal = 0;
        for (String s : tipoReparacionArray) {
            // si es "" no se hace nada
            if (s.isEmpty()) {
                continue;
            }
            int tipoReparacionNum = Integer.parseInt(s);
            montoTotal += preciosMotor[tipoReparacionNum][motorNum];
        }
        return montoTotal;
    }

    public double descuentoCantidadReparaciones(ReparacionEntity reparacion) {

        int cantidadReparaciones = reparacionRepository.findByVehiculoID(reparacion.getIdVehiculo()).size();
        //print in console the cantidadReparaciones
        //si cantidad de reparaciones supera 10 entonces cantidadReparaciones = 9
        if (cantidadReparaciones >= 10) {
            cantidadReparaciones = 9;
        }
        System.out.println("cantidadReparaciones: " + cantidadReparaciones);
        //obtener el NUMERO de motor del vehiculo
        int motorNum = numeroMotor(reparacion);
        System.out.println("motornum: " + motorNum);

        return descuentosMotor[cantidadReparaciones][motorNum];
    }
    public double descuentoDiaAtencion(ReparacionEntity reparacion) {
        //obtener la fecha de la reparacion
        LocalDateTime fechaReparacion = reparacion.getFechaHoraIngreso();
        int diaSemana = fechaReparacion.getDayOfWeek().getValue();
        int horaIngreso = fechaReparacion.getHour();
        //si la reparacion se realiza un dia lunes o jueves entre las  09:00 hrs y las 12:00 hrs. se aplica un descuento del 10%
        if ((diaSemana == 1 || diaSemana == 4) && (horaIngreso >= 9 && horaIngreso <= 12)) {
            return 0.1;
        }
        return 0;
    }

    public double descuentoMarca(ReparacionEntity reparacion) {
        String marca = vehiculoRepository.findById(Long.valueOf(reparacion.getIdVehiculo())).get().getMarca();
        int mes = reparacion.getFechaHoraIngreso().getMonth().getValue();
        int anio = reparacion.getFechaHoraIngreso().getYear();
        LocalDateTime fechaReparacion = LocalDateTime.of(anio, mes, 1, 0, 0);

        MarcaEntity marcaEntity = marcaService.findByFechaBonoMarca(fechaReparacion, marca);
        if (marcaEntity == null) {
            return 0;
        }
        return marcaEntity.getDescuento();
    }

    public double recargoKilometraje(ReparacionEntity reparacion) {
        //obtener el kilometraje del vehiculo
        int kilometraje = vehiculoRepository.findById(Long.valueOf(reparacion.getIdVehiculo())).get().getKilometraje();
        //si el kilometraje es mayor a 100000 km se aplica un recargo del 10%
        int tipoAutoNum = numeroTipoVehiculo(reparacion);
        //print in console each option
        System.out.println("tipoAutoNum: " + tipoAutoNum);
        if (kilometraje <= 5000) {
            return recargoKilometraje[4][tipoAutoNum];
        } else if (kilometraje <= 12000) {
            return recargoKilometraje[4][tipoAutoNum];
        } else if (kilometraje <= 25000) {
            return recargoKilometraje[4][tipoAutoNum];
        } else if (kilometraje <= 40000) {
            return recargoKilometraje[4][tipoAutoNum];
        } else {
            return recargoKilometraje[4][tipoAutoNum];
        }
    }

    public double recargoAntiguedadVehiculo(ReparacionEntity reparacion) {
        //obtener la fecha de fabricacion del vehiculo
        // Assuming "getAnnoFabricacion()" returns a String representing the year
        String annoFabricacionStr = vehiculoRepository.findById(Long.valueOf(reparacion.getIdVehiculo())).get().getAnnoFabricacion();
        // Parsing the string to an integer
        int fechaFabricacion = Integer.parseInt(annoFabricacionStr);
        //obtener la fecha de la reparacion
        int fechaReparacion = reparacion.getFechaHoraIngreso().toLocalDate().getYear();
        //obtener el tipo de auto
        int tipoAutoNum = numeroTipoVehiculo(reparacion);
        int diferenciaAnios = fechaReparacion - fechaFabricacion;
        //si el vehiculo tiene mas de 10 años de antiguedad se aplica un recargo del 10%
        if (diferenciaAnios <= 5) {
            return recargoAntiguedad[0][tipoAutoNum];
        } else if (diferenciaAnios <= 10) {
            return recargoAntiguedad[1][tipoAutoNum];
        } else if (diferenciaAnios <= 15) {
            return recargoAntiguedad[2][tipoAutoNum];
        } else {
            return recargoAntiguedad[3][tipoAutoNum];
        }
    }

    public double recargoDiasDesdeSalida(ReparacionEntity reparacion) {
        //obtener la fecha de la reparacion
        LocalDateTime fechaSalida = reparacion.getFechaHoraSalida();
        LocalDateTime fechaRetiro = reparacion.getFechaHoraRetiro();
        //obtener la cantidad de dias entre la fecha de salida y la fecha de retiro
        int diasDiferencia = Period.between(fechaSalida.toLocalDate(), fechaRetiro.toLocalDate()).getDays();
        //si la cantidad de dias es mayor a 3 se aplica un recargo del 10%
        if (diasDiferencia <= 0) {
            return 0;
        }
        return (diasDiferencia * 5) * 0.01;
    }


    public double descuentos(ReparacionEntity reparacion) {
        return descuentoCantidadReparaciones(reparacion) + descuentoDiaAtencion(reparacion);
    }

    public double recargos(ReparacionEntity reparacion) {
        if (reparacion.getFechaHoraSalida() == null || reparacion.getFechaHoraRetiro() == null) {
            return recargoKilometraje(reparacion) + recargoAntiguedadVehiculo(reparacion);
        }

        return recargoKilometraje(reparacion) + recargoAntiguedadVehiculo(reparacion) + recargoDiasDesdeSalida(reparacion);
    }

    public Pair<Integer, String> calcularPago(ReparacionEntity reparacion) {
        double monto = precioReparacionVSMotor(reparacion);
        double descuento = descuentos(reparacion);
        double recargo = recargos(reparacion);
        double descuentoMarca = descuentoMarca(reparacion);

        // Round up each calculated value to ensure it doesn't exceed the bounds of an int
        double total = Math.ceil(monto - (monto * descuento) + (monto * recargo) - descuentoMarca);
        //make a string with all the values
        String totalPagar = "monto: " + String.valueOf(monto) + "\n descuento: " + String.valueOf(descuento*monto) + "\n recargo: " + String.valueOf(recargo*monto) + "\n descuentoMarca: " + String.valueOf(descuentoMarca) + "\n total: " + String.valueOf(total);

        // Convert the total to an int
        return Pair.of((int) total, totalPagar);
    }
}