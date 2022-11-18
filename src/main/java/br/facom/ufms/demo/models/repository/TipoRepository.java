package br.facom.ufms.demo.models.repository;

import br.facom.ufms.demo.models.entity.Tipo;
import org.springframework.data.repository.CrudRepository;

public interface TipoRepository extends CrudRepository<Tipo, Integer> {
}

