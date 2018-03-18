package br.com.bigdelivery.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.bigdelivery.model.Customer;

//@PreAuthorize("hasRole('ROLE_USER')")
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Optional<Customer> findByName(@Param("name") String name);
	
	Optional<Customer> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

/*
	@SuppressWarnings("unchecked")
	@Override
	@PreAuthorize("#customer == null or #customer?.firstName == authentication?.name")
	Customer save(@Param("customer") Customer customer);
	
	@Override
	@PreAuthorize("@customerRepository.findOne(#id)?.firstName == authentication?.name")
	void deleteById(@Param("id") Long id);

	@Override
	@PreAuthorize("#customer?.firstName == authentication?.name")
	void delete(@Param("customer") Customer customer);
*/	
}
