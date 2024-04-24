package tingeso.autofix.services;
import tingeso.autofix.entities.ReparacionEntity;

import java.time.LocalDateTime;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PagoService {
    @Autowired
    VehiculoService vehiculoService;

    @Autowired
    ReparacionService reparacionService;

    @Autowired
    MarcaService marcaService;

// Definir una matriz de precios donde las filas representan los tipos de reparaciones
// y las columnas representan los tipos de motor.
int[][] preciosMotor = {
    // tipo de motor 1, 2, 3, 4 respectivamente
    {10, 20, 30, 40}, // reparación tipo 1
    {15, 25, 35, 45}, // reparación tipo 2
    {12, 22, 32, 42}, // reparación tipo 3
    {12, 22, 32, 42}, // reparación tipo 4
    {12, 22, 32, 42}, // reparación tipo 5
    {12, 22, 32, 42}, // reparación tipo 6
    {12, 22, 32, 42}, // reparación tipo 7
    {12, 22, 32, 42}, // reparación tipo 8
    {12, 22, 32, 42}, // reparación tipo 9
    {12, 22, 32, 42}, // reparación tipo 10
    {12, 22, 32, 42}, // reparación tipo 11

};

int [][] descuentosMotor = {
        // tipo de motor 1, 2, 3, 4 respectivamente
        {10, 20, 30, 40}, // cantidad de reparaciones
        {15, 25, 35, 45}, // cantidad de reparaciones
        {12, 22, 32, 42}, // cantidad de reparaciones
        {12, 22, 32, 42}, // cantidad de reparaciones
        {12, 22, 32, 42}, // cantidad de reparaciones
        {12, 22, 32, 42}, // cantidad de reparaciones
        {12, 22, 32, 42}, // cantidad de reparaciones
        {12, 22, 32, 42}, // cantidad de reparaciones
        {12, 22, 32, 42}, // cantidad de reparaciones
        {12, 22, 32, 42}, // cantidad de reparaciones
};

int [][] recargoKilometraje ={
    // tipos de autos 1,2,3,4,5 respectivamente
        {10, 20, 30, 40, 50}, // cantidad de Kilometros
        {15, 25, 35, 45, 50}, // cantidad de Kilometros
        {12, 22, 32, 42, 50}, // cantidad de 
        {12, 22, 32, 42, 50}, // cantidad de

};

int [][] recargoAntiguedad ={
    // tipos de autos 1,2,3,4,5 respectivamente
        {10, 20, 30, 40, 50}, // años de antiguedad
        {15, 25, 35, 45, 50}, // años de antiguedad
        {12, 22, 32, 42, 50}, // años de antiguedad
        {12, 22, 32, 42, 50}, // años de antiguedad
};

String [] motores = {"Gasolina", "Diésel", "Híbrido", "Eléctrico"};

String [] marcas = {"Toyota", "Kia", "Honda", "Ford", "Chevrolet", "Hyundai"};

String [] tipoAuto = {"Sedán", "Hatchback", "SUV", "Pickup", "Furgoneta"};


public int numeroMotor(ReparacionEntity reparacion) {
    //obtener el tipo de motor del vehiculo
    String motor = vehiculoService.obtenerPorId(Long.valueOf(reparacion.getIdVehiculo())).getTipoMotor();
    int motorNum = 0;
    for (int i = 0; i < motores.length; i++) {
        if (motores[i].equals(motor)) {
            motorNum = i;
            break;
        }
    }
    return motorNum;
}

public int numeroMarca(ReparacionEntity reparacion) {
    //obtener la marca del vehiculo
    String marca = vehiculoService.obtenerPorId(Long.valueOf(reparacion.getIdVehiculo())).getMarca();
    int marcaNum = 0;
    for (int i = 0; i < marcas.length; i++) {
        if (marcas[i].equals(marca)) {
            marcaNum = i;
            break;
        }
    }
    return marcaNum;
}

public int numeroTipoVehiculo(ReparacionEntity reparacion) {
    //obtener el tipo de auto del vehiculo
    String tipo = vehiculoService.obtenerPorId(Long.valueOf(reparacion.getIdVehiculo())).getTipoVehiculo();
    int tipoNum = 0;
    for (int i = 0; i < tipoAuto.length; i++) {
        if (tipoAuto[i].equals(tipo)) {
            tipoNum = i;
            break;
        }
    }
    return tipoNum;
}

public int precioReparacionVSMotor(ReparacionEntity reparacion) {
    //obtener el tipo de motor del vehiculo
    int motorNum = numeroMotor(reparacion);
    
    //sea una string separada por comas con los tipos de reparacion, obtener cada tipo de reparacion en un arreglo donde cada vez que se encuentre una coma se separa
    String tipoReparacion = reparacion.getTipoReparacion();
    String[] tipoReparacionArray = tipoReparacion.split(",");

    int montoTotal = 0;
    for (int i = 0; i < tipoReparacionArray.length; i++) {
        int tipoReparacionNum = Integer.parseInt(tipoReparacionArray[i]);
        montoTotal += preciosMotor[tipoReparacionNum][motorNum];
    }

    return montoTotal;
}

public int descuentoCantidadReparaciones(ReparacionEntity reparacion){
    int descuento = 0;

    int cantidadReparaciones = reparacionService.obtenerReparacionesPorIdVehiculo(reparacion.getIdVehiculo()).size();
    //obtener el NUMERO de motor del vehiculo
    int motorNum = numeroMotor(reparacion);

    descuento = descuentosMotor[cantidadReparaciones][motorNum];
    
    return descuento;
}

public int descuentoDiaAtencion(ReparacionEntity reparacion){
    int descuento = 0;
    //obtener la fecha de la reparacion
    LocalDateTime fechaReparacion = reparacion.getFechaHoraIngreso();
    //obtener el dia de la semana de la reparacion
    int diaSemana = fechaReparacion.getDayOfWeek().getValue();
    int horaIngreso = fechaReparacion.getHour();
    //si la reparacion se realiza un dia lunes o jueves entre las  09:00 hrs y las 12:00 hrs. se aplica un descuento del 10%
    if ((diaSemana == 1 || diaSemana == 4) && (horaIngreso >= 9 && horaIngreso <= 12)) {
        descuento = 10;
    }
    return descuento;
}

public int descuentoMarca(ReparacionEntity reparacion){
    int descuento = 0;
    //obtener la marca del vehiculo
    String marca = vehiculoService.obtenerPorId(Long.valueOf(reparacion.getIdVehiculo())).getMarca();
    //verificar si la marca del vehiculo tiene bonos disponibles y aplicar el descuento correspondiente
    int mes = reparacion.getFechaHoraIngreso().getMonth().getValue();
    int anio = reparacion.getFechaHoraIngreso().getYear();
    LocalDateTime fechaReparacion = LocalDateTime.of(anio, mes, 1, 0, 0);
    marcaService.findByFechaBonoMarca(fechaReparacion.toString(), marca);
    return descuento;
}

public int recargoKilometraje(ReparacionEntity reparacion){
    int recargo = 0;
    //obtener el kilometraje del vehiculo
    int kilometraje = vehiculoService.obtenerPorId(Long.valueOf(reparacion.getIdVehiculo())).getKilometraje();
    //si el kilometraje es mayor a 100000 km se aplica un recargo del 10%
    int tipoAutoNum = numeroTipoVehiculo(reparacion);
    if (kilometraje <= 5000) {
        recargo = recargoKilometraje[tipoAutoNum][0];
    }
    else if (kilometraje <= 12000) {
        recargo = recargoKilometraje[tipoAutoNum][1];
    }
    else if (kilometraje <= 25000) {
        recargo = recargoKilometraje[tipoAutoNum][2];
    }
    else if (kilometraje <= 40000) {
        recargo = recargoKilometraje[tipoAutoNum][3];
    }
    else {
        recargo = recargoKilometraje[tipoAutoNum][4];
    }
    return recargo;
}

public int recargoAntiguedadVehiculo(ReparacionEntity reparacion){
    int recargo = 0;
    //obtener la fecha de fabricacion del vehiculo
    // Assuming "getAnnoFabricacion()" returns a String representing the year
    String annoFabricacionStr = vehiculoService.obtenerPorId(Long.valueOf(reparacion.getIdVehiculo())).getAnnoFabricacion();
    // Parsing the string to an integer
    int fechaFabricacion = Integer.parseInt(annoFabricacionStr);
    //obtener la fecha de la reparacion
    int fechaReparacion = reparacion.getFechaHoraIngreso().toLocalDate().getYear();
    //obtener el tipo de auto
    int tipoAutoNum = numeroTipoVehiculo(reparacion);
    int diferenciaAnios = fechaReparacion - fechaFabricacion;
    //si el vehiculo tiene mas de 10 años de antiguedad se aplica un recargo del 10%
    if (diferenciaAnios <= 5) {
        recargo = recargoAntiguedad[tipoAutoNum][0];
    }
    else if (diferenciaAnios <= 10) {
        recargo = recargoAntiguedad[tipoAutoNum][1];
    }
    else if (diferenciaAnios <= 15) {
        recargo = recargoAntiguedad[tipoAutoNum][2];
    }
    else {
        recargo = recargoAntiguedad[tipoAutoNum][3];
    }

    return recargo;
}

public int recargoDiasDesdeSalida(ReparacionEntity reparacion){
    int recargo = 0;
    //obtener la fecha de la reparacion
    LocalDateTime fechaSalida = reparacion.getFechaHoraSalida();
    LocalDateTime fechaRetiro = reparacion.getFechaHoraRetiro();
    //obtener la cantidad de dias entre la fecha de salida y la fecha de retiro
    int diasDiferencia = Period.between(fechaSalida.toLocalDate(), fechaRetiro.toLocalDate()).getDays();
    //si la cantidad de dias es mayor a 3 se aplica un recargo del 10%
    if (diasDiferencia > 0) {
        for (int i = 0; i < diasDiferencia; i++) {
            recargo += 5;
        }
    }
    return recargo;
}


public int descuentos(ReparacionEntity reparacion){
    int descuento = descuentoCantidadReparaciones(reparacion) + descuentoDiaAtencion(reparacion);
    return descuento;
    }
public int recargos(ReparacionEntity reparacion){
    int recargo = recargoKilometraje(reparacion) + recargoAntiguedadVehiculo(reparacion) + recargoDiasDesdeSalida(reparacion);
    return recargo;
    }

public int totalPagar(ReparacionEntity reparacion){
    int monto = precioReparacionVSMotor(reparacion);
    int total = monto - monto*descuentos(reparacion) + monto*recargos(reparacion) + descuentoMarca(reparacion);
    return total;
    }

}