package br.facom.ufms.demo.controllers;

import br.facom.ufms.demo.models.dto.FraquezaDTO;
import br.facom.ufms.demo.models.dto.TipoDTO;
import br.facom.ufms.demo.models.entity.Fraqueza;
import br.facom.ufms.demo.models.entity.Tipo;
import br.facom.ufms.demo.models.repository.FraquezaRepository;
import br.facom.ufms.demo.models.repository.TipoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tipo")
public class TipoController {

    @Autowired
    TipoRepository tipoRepository;

    @GetMapping
    public ResponseEntity<Object> getAllTipos() {
        return ResponseEntity.status(HttpStatus.OK).body(tipoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> saveTipos(@RequestBody TipoDTO tipoDTO) {
        Tipo tipoEntitiy = new Tipo();
        BeanUtils.copyProperties(tipoDTO, tipoEntitiy);

        return ResponseEntity.status(HttpStatus.OK).body(tipoRepository.save(tipoEntitiy));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTipos( @PathVariable Integer id, @RequestBody TipoDTO tipoDTO) {
        Optional<Tipo> tipoExists = tipoRepository.findById(id);

        if(!tipoExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar esse tipo");
        }

        Tipo tipoEntitiy = tipoExists.get();

        BeanUtils.copyProperties(tipoDTO, tipoEntitiy);

        return ResponseEntity.status(HttpStatus.OK).body(tipoRepository.save(tipoEntitiy));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTipos( @PathVariable Integer id) {

        Optional<Tipo> tipoExists = tipoRepository.findById(id);

        if (!tipoExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar esse tipo");
        }

        tipoRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Tipo deletado com sucesso");
    }
}
