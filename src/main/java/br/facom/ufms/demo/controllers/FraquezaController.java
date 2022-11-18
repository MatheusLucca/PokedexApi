package br.facom.ufms.demo.controllers;

import br.facom.ufms.demo.models.dto.FraquezaDTO;
import br.facom.ufms.demo.models.entity.Fraqueza;
import br.facom.ufms.demo.models.repository.FraquezaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/fraqueza")
public class FraquezaController {

    @Autowired
    FraquezaRepository fraquezaRepository;

    @GetMapping
    public ResponseEntity<Object> getAllFraquezas() {
        return ResponseEntity.status(HttpStatus.OK).body(fraquezaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> saveFraquezas(@RequestBody FraquezaDTO fraquezaDTO) {
        Fraqueza fraquezaEntitiy = new Fraqueza();
        BeanUtils.copyProperties(fraquezaDTO, fraquezaEntitiy);

        return ResponseEntity.status(HttpStatus.OK).body(fraquezaRepository.save(fraquezaEntitiy));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFraquezas( @PathVariable Integer id, @RequestBody FraquezaDTO fraquezaDTO) {
        Optional<Fraqueza> fraquezaExists = fraquezaRepository.findById(id);

        if(!fraquezaExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar a fraqueza");
        }

        Fraqueza fraquezaEntitiy = fraquezaExists.get();

        BeanUtils.copyProperties(fraquezaDTO, fraquezaEntitiy);

        return ResponseEntity.status(HttpStatus.OK).body(fraquezaRepository.save(fraquezaEntitiy));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFraquezas( @PathVariable Integer id) {

        Optional<Fraqueza> fraquezaExists = fraquezaRepository.findById(id);

        if (!fraquezaExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar a fraqueza");
        }

        fraquezaRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Fraqueza deletada com sucesso");
    }
}
