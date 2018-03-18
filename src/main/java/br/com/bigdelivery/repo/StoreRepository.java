package br.com.bigdelivery.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.bigdelivery.model.Store;

public interface StoreRepository extends CrudRepository<Store, Long> {
	
	Iterable<Store> findByNameStartingWith(@Param("name") String name);

}
