package com.financorp.serf.service;

import com.financorp.serf.model.Filial;
import com.financorp.serf.repository.FilialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilialService {
    
    @Autowired
    private FilialRepository filialRepository;
    
    public List<Filial> getAllFiliales() {
        return filialRepository.findAll();
    }
    
    public List<Filial> getActiveFiliales() {
        return filialRepository.findByActivaTrue();
    }
    
    public Filial getFilialById(Long id) {
        return filialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filial no encontrada"));
    }
    
    public Filial getFilialByCodigo(String codigo) {
        return filialRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Filial no encontrada"));
    }
    
    public List<Filial> getFilialByPais(String pais) {
        return filialRepository.findByPais(pais);
    }
    
    public Filial createFilial(Filial filial) {
        return filialRepository.save(filial);
    }
    
    public Filial updateFilial(Long id, Filial filial) {
        Filial existing = getFilialById(id);
        existing.setNombre(filial.getNombre());
        existing.setPais(filial.getPais());
        existing.setCiudad(filial.getCiudad());
        existing.setMoneda(filial.getMoneda());
        existing.setResponsable(filial.getResponsable());
        existing.setActiva(filial.getActiva());
        return filialRepository.save(existing);
    }
    
    public void deleteFilial(Long id) {
        filialRepository.deleteById(id);
    }
}
