package br.com.bigdelivery.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.bigdelivery.model.Cousine;

public interface CousineRepository extends CrudRepository<Cousine, Long> {
	
	Iterable<Cousine> findByNameStartingWith(@Param("name") String name);

}
