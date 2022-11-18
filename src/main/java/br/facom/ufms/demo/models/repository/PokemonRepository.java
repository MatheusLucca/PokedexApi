package br.facom.ufms.demo.models.repository;

import br.facom.ufms.demo.models.entity.Pokemon;
import br.facom.ufms.demo.models.entity.Tipo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PokemonRepository extends CrudRepository<Pokemon, Integer> {
    List<Pokemon> findAllByTipos(Tipo tipos);

}

