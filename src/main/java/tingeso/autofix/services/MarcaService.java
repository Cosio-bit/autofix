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

    public MarcaEntity findByNombre(String nombre) {
        Optional<MarcaEntity> optionalMarca = marcaRepository.findByNombre(nombre);
        return optionalMarca.orElse(null);
    }

    public MarcaEntity findByFechaBonoMarca(String fechaBono, String marca) {
        Optional<MarcaEntity> optionalMarca = marcaRepository.findByFechaBonoMarca(fechaBono, marca);
        //si la marca no existe o no le quedan bonos, retornar null
        if (!optionalMarca.isPresent() || optionalMarca.get().getCantidadBonos() == 0) {
            return null;
        }
        optionalMarca.get().setCantidadBonos(optionalMarca.get().getCantidadBonos() - 1);
        marcaRepository.save(optionalMarca.get());

        return optionalMarca.orElse(null);
    }

    //ejemplo de cada linea a procesar Toyota: 5 bonos de 70.000 pesos
    public void processFile(MultipartFile file, String filename) throws IOException {
        //el filename es la fecha de la forma dia-mes-año, separarlo y convertirlo a LocalDateTime
        LocalDateTime fechaBono = LocalDateTime.parse(filename, DateTimeFormatter.ofPattern("dd-MM-yyyy")).withDayOfMonth(1);
        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            MarcaEntity marca = new MarcaEntity();
            String[] data = line.split(":");
            marca.setNombre(data[0]);
            marca.setCantidadBonos(Integer.parseInt(data[1].split(" ")[0]));
            marca.setDescuento(Integer.parseInt(data[1].split(" ")[2]));
            marca.setFechaBono(fechaBono);
            marcaRepository.save(marca);
        }
    }


}
