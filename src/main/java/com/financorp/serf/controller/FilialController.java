package com.financorp.serf.controller;

import com.financorp.serf.model.Filial;
import com.financorp.serf.service.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filiales")
@CrossOrigin(origins = "*")
public class FilialController {
    
    @Autowired
    private FilialService filialService;
    
    @GetMapping
    public ResponseEntity<List<Filial>> getAllFiliales() {
        return ResponseEntity.ok(filialService.getAllFiliales());
    }
    
    @GetMapping("/activas")
    public ResponseEntity<List<Filial>> getActiveFiliales() {
        return ResponseEntity.ok(filialService.getActiveFiliales());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Filial> getFilialById(@PathVariable Long id) {
        return ResponseEntity.ok(filialService.getFilialById(id));
    }
    
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Filial> getFilialByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(filialService.getFilialByCodigo(codigo));
    }
    
    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<Filial>> getFilialByPais(@PathVariable String pais) {
        return ResponseEntity.ok(filialService.getFilialByPais(pais));
    }
    
    @PostMapping
    public ResponseEntity<Filial> createFilial(@RequestBody Filial filial) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(filialService.createFilial(filial));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Filial> updateFilial(@PathVariable Long id, 
                                               @RequestBody Filial filial) {
        return ResponseEntity.ok(filialService.updateFilial(id, filial));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilial(@PathVariable Long id) {
        filialService.deleteFilial(id);
        return ResponseEntity.noContent().build();
    }
}
