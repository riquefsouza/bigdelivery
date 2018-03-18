package br.com.bigdelivery.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.bigdelivery.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	Iterable<Product> findByDescriptionStartingWith(@Param("description") String description);
}
