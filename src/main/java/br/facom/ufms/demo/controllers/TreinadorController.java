package br.facom.ufms.demo.controllers;

import br.facom.ufms.demo.models.dto.TreinadorDTO;
import br.facom.ufms.demo.models.entity.Pokebola;
import br.facom.ufms.demo.models.entity.Pokemon;
import br.facom.ufms.demo.models.entity.Treinador;
import br.facom.ufms.demo.models.repository.PokebolaRepository;
import br.facom.ufms.demo.models.repository.PokemonRepository;
import br.facom.ufms.demo.models.repository.TreinadorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/treinador")
public class TreinadorController {

    @Autowired
    TreinadorRepository treinadorRepository;

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    PokebolaRepository pokebolaRepository;

    @GetMapping
    public ResponseEntity<Object> getAllTreinadores() {
        return ResponseEntity.status(HttpStatus.OK).body(treinadorRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> saveTreinador(@RequestBody TreinadorDTO treinadorDTO) {
        Treinador treinador = new Treinador();
        BeanUtils.copyProperties(treinadorDTO, treinador);

        for (Integer pokemonId : treinadorDTO.getPokebolas()) {
            Pokebola pokebola = new Pokebola();
            if (pokemonId == -1){
                pokebola.setPokemon(null);
            } else {
                Optional<Pokemon> pokemon = pokemonRepository.findById(pokemonId);
                if (pokemon.isPresent()) {
                    pokebola.setPokemon(pokemon.get());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pokemon [" + pokemonId + "] não encontrado");
                }
            }
            pokebolaRepository.save(pokebola);
            treinador.getPokebolas().add(pokebola);
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(treinadorRepository.save(treinador));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTreinador(@PathVariable Integer id, @RequestBody TreinadorDTO treinadorDTO) {
        Optional<Treinador> treinadorExists = treinadorRepository.findById(id);

        if (!treinadorExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Treinador não encontrado");
        }

        Treinador treinador = treinadorExists.get();
        BeanUtils.copyProperties(treinadorDTO, treinador);

        List<Pokebola> pokebolas = new ArrayList<Pokebola>();

        treinador.setPokebolas(pokebolas);
        for (Integer pokemonId : treinadorDTO.getPokebolas()) {
            Pokebola pokebola = new Pokebola();
            if (pokemonId == -1){
                pokebola.setPokemon(null);
            } else {
                Optional<Pokemon> pokemon = pokemonRepository.findById(pokemonId);
                if (pokemon.isPresent()) {
                    pokebola.setPokemon(pokemon.get());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pokemon [" + pokemonId + "] não encontrado");
                }
            }
            pokebolaRepository.save(pokebola);
            treinador.getPokebolas().add(pokebola);
        }
        return ResponseEntity.status(HttpStatus.OK).body(treinadorRepository.save(treinador));


    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTreinador(@PathVariable Integer id) {
        Optional<Treinador> treinadorExists = treinadorRepository.findById(id);

        if (!treinadorExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Treinador não encontrado");
        }

        treinadorRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Treinador deletado com sucesso!");
    }

}
