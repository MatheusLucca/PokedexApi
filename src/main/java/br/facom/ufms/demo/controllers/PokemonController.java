package br.facom.ufms.demo.controllers;

import br.facom.ufms.demo.models.dto.PokemonDTO;
import br.facom.ufms.demo.models.entity.Fraqueza;
import br.facom.ufms.demo.models.entity.Habilidade;
import br.facom.ufms.demo.models.entity.Pokemon;
import br.facom.ufms.demo.models.entity.Tipo;
import br.facom.ufms.demo.models.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    FraquezaRepository fraquezaRepository;

    @Autowired
    HabilidadeRepository habilidadeRepository;

    @Autowired
    TipoRepository tipoRepository;

    @Autowired
    PokebolaRepository pokebolaRepository;

    @GetMapping
    public ResponseEntity<Object> getAllPokemons() {
        return ResponseEntity.status(HttpStatus.OK).body(pokemonRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPokemonById(@PathVariable Integer id) {
        Optional<Pokemon> pokemon = pokemonRepository.findById(id);
        if (pokemon.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(pokemon.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon não encontrado");
        }
    }
    @GetMapping("/tipo/{id}")
    public ResponseEntity<Object> getPokemonByTipo(@PathVariable Integer id) {
        Optional<Tipo> tipo = tipoRepository.findById(id);
        if (!tipo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pokemonRepository.findAllByTipos(tipo.get()));
    }

    @GetMapping("/maiscomuns")
    public ResponseEntity<Object> getTop10PokemonsMaisComuns() {
        List <Integer> ids = pokebolaRepository.findTop10Pokemons();
        List <Pokemon> pokemons = new ArrayList<Pokemon>();
        for (Integer id : ids) {
            pokemons.add(pokemonRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(pokemons);
    }

    @PostMapping
    public ResponseEntity<Object> savePokemons(@RequestBody PokemonDTO pokemonDTO) {
        Pokemon newPokemon = new Pokemon();
        BeanUtils.copyProperties(pokemonDTO, newPokemon);

        for (Integer fraquezaId: pokemonDTO.getFraquezas()) {
            Optional<Fraqueza> fraquezaPokemon = fraquezaRepository.findById(fraquezaId);

            if (!fraquezaPokemon.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fraqueza [" + fraquezaId + "] não encontrada");
            }
            newPokemon.getFraquezas().add(fraquezaPokemon.get());
        }

        for (Integer tipoId: pokemonDTO.getTipos()) {
            Optional<Tipo> tipoPokemon = tipoRepository.findById(tipoId);

            if (!tipoPokemon.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo [" + tipoId + "] não encontrado");
            }
            newPokemon.getTipos().add(tipoPokemon.get());
        }

        for (Integer habilidadeId: pokemonDTO.getHabilidades()) {
            Optional<Habilidade> habilidadePokemon = habilidadeRepository.findById(habilidadeId);

            if (!habilidadePokemon.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habilidade [" + habilidadeId + "] não encontrada");
            }
            newPokemon.getHabilidades().add(habilidadePokemon.get());
        }

        return ResponseEntity.status(HttpStatus.OK).body(pokemonRepository.save(newPokemon));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePokemon(@PathVariable Integer id, @RequestBody PokemonDTO pokemonDTO) {
        Optional<Pokemon> pokemonExists = pokemonRepository.findById(id);

        if (!pokemonExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokemon não encontrado");
        }

        Pokemon updatePokemon = pokemonExists.get();
        BeanUtils.copyProperties(pokemonDTO, updatePokemon);

        List<Fraqueza> fraquezas = new ArrayList<Fraqueza>();
        List<Tipo> tipos = new ArrayList<Tipo>();
        List<Habilidade> habilidades = new ArrayList<Habilidade>();

        updatePokemon.setFraquezas(fraquezas);
        updatePokemon.setHabilidades(habilidades);
        updatePokemon.setTipos(tipos);

        for (Integer fraquezaId: pokemonDTO.getFraquezas()) {
            Optional<Fraqueza> fraquezaPokemon = fraquezaRepository.findById(fraquezaId);

            if (!fraquezaPokemon.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fraqueza [" + fraquezaId + "] não encontrada");
            }

            updatePokemon.getFraquezas().add(fraquezaPokemon.get());
        }

        for (Integer tipoId: pokemonDTO.getTipos()) {
            Optional<Tipo> tipoPokemon = tipoRepository.findById(tipoId);

            if (!tipoPokemon.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo [" + tipoId + "] não encontrado");
            }
            updatePokemon.getTipos().add(tipoPokemon.get());
        }

        for (Integer habilidadeId: pokemonDTO.getHabilidades()) {
            Optional<Habilidade> habilidadePokemon = habilidadeRepository.findById(habilidadeId);

            if (!habilidadePokemon.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habilidade [" + habilidadeId + "] não encontrada");
            }
            updatePokemon.getHabilidades().add(habilidadePokemon.get());
        }


        return ResponseEntity.status(HttpStatus.OK).body(pokemonRepository.save(updatePokemon));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePokemon(@PathVariable Integer id) {
        Optional<Pokemon> pokemonExists = pokemonRepository.findById(id);

        if (!pokemonExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar o pokemon");
        }

        pokemonRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Pokemon deletado com sucesso");

    }

}
