package tingeso.autofix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.autofix.entities.MarcaEntity;
import tingeso.autofix.repositories.MarcaRepository;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MarcaService {
    @Autowired
    MarcaRepository marcaRepository;

    public ArrayList<MarcaEntity> obtenerMarcas(){
        return (ArrayList<MarcaEntity>) marcaRepository.findAll();
    }

    public MarcaEntity guardarMarca(MarcaEntity marca){
        int mes = marca.getFechaBono().getMonth().getValue();
        int anio = marca.getFechaBono().getYear();
        LocalDateTime fecha = LocalDateTime.of(anio, mes, 1, 0, 0);
        marca.setFechaBono(fecha);
        //si la marca ya existe para la fecha, sobre escribir la cantidad de bonos
        Optional<MarcaEntity> optionalMarca = Optional.ofNullable(marcaRepository.findByNombreAndFechaBono(marca.getFechaBono(), marca.getNombre()));
        if (optionalMarca.isPresent()) {
            MarcaEntity marcaExistente = optionalMarca.get();
            marcaExistente.setCantidadBonos(marcaExistente.getCantidadBonos() + marca.getCantidadBonos());
            marcaRepository.save(marcaExistente);
            return marcaExistente;
        }
        
        marcaRepository.save(marca);
        return marca;
    }
    public MarcaEntity updateMarca(MarcaEntity marca) {
        return marcaRepository.save(marca);
    }
    public boolean deleteMarca(Long id) throws Exception {
        try {
            marcaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public MarcaEntity findById(Long marcaId) {
        Optional<MarcaEntity> optionalMarca = marcaRepository.findById(marcaId);
        return optionalMarca.orElse(null);
    }
    public Optional<MarcaEntity> findByNombre(String nombre) {
        Optional<MarcaEntity> optionalMarca = marcaRepository.findByNombre(nombre);
        return Optional.ofNullable(optionalMarca.orElse(null));
    }
    public MarcaEntity findByFechaBonoMarca(LocalDateTime fechaBono, String marca) {

        MarcaEntity optionalMarca = marcaRepository.findByNombreAndFechaBono(fechaBono, marca);
        //si la marca no existe o no le quedan bonos, retornar null
        if (optionalMarca == null || optionalMarca.getCantidadBonos() == 0) {
            return null;
        }
        optionalMarca.setCantidadBonos(optionalMarca.getCantidadBonos() - 1);
        marcaRepository.save(optionalMarca);

        return optionalMarca;
    }

}
