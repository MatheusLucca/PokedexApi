package br.facom.ufms.demo.models.repository;

import br.facom.ufms.demo.models.entity.Pokebola;
import br.facom.ufms.demo.models.entity.Pokemon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PokebolaRepository extends CrudRepository<Pokebola, Integer> {
    @Query(value = "select p.id from pokemons p join pokebolas pb on p.id = pb.pokemon_id group by p.id order by count(*) desc limit 10;", nativeQuery = true)
    List<Integer> findTop10Pokemons();
}

