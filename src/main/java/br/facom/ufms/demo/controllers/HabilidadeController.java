package br.facom.ufms.demo.controllers;

import br.facom.ufms.demo.models.dto.HabilidadeDto;
import br.facom.ufms.demo.models.entity.Habilidade;
import br.facom.ufms.demo.models.repository.HabilidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/habilidade")
public class HabilidadeController {
    @Autowired
    HabilidadeRepository habilidadeRepository;

    @GetMapping
    public ResponseEntity<Object> getAllHabilidades() {
        return ResponseEntity.status(HttpStatus.OK).body(habilidadeRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> saveHabilidades(@RequestBody HabilidadeDto habilidadeDto) {
        Habilidade habilidadeEntitiy = new Habilidade();
        BeanUtils.copyProperties(habilidadeDto, habilidadeEntitiy);

        return ResponseEntity.status(HttpStatus.OK).body(habilidadeRepository.save(habilidadeEntitiy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateHabilidades( @PathVariable Integer id, @RequestBody HabilidadeDto habilidadeDto) {
        Optional<Habilidade> habilidadeExists = habilidadeRepository.findById(id);

        if(!habilidadeExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar a habilidade");
        }

        Habilidade habilidadeEntitiy = habilidadeExists.get();

        BeanUtils.copyProperties(habilidadeDto, habilidadeEntitiy);

        return ResponseEntity.status(HttpStatus.OK).body(habilidadeRepository.save(habilidadeEntitiy));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHabilidades( @PathVariable Integer id) {

        Optional<Habilidade> habilidadeExists = habilidadeRepository.findById(id);

        if (!habilidadeExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar a habilidade");
        }

        habilidadeRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Habilidade deletada com sucesso");
    }


}
