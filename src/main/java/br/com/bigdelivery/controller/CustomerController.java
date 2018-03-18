package br.com.bigdelivery.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.bigdelivery.model.Customer;
import br.com.bigdelivery.repo.CustomerRepository;

@RestController
@RequestMapping("/api/v1/Customer")
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	
	@Autowired
	CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Customer> getCustomer(@PathVariable(value = "id") Long id) {
		Optional<Customer> obj = customerRepository.findById(id);
		if (obj.isPresent())
			return new ResponseEntity<Customer>(obj.get(), HttpStatus.OK);
		else
			return ResponseEntity.notFound().build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> postCustomer(@RequestBody Customer input){
		Customer obj = new Customer(input.getName(),
				input.getAddress(), input.getEmail(), input.getPassword());
		
		Customer result = customerRepository.save(obj);
		
		if (result!=null)
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		else
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);	
	}
	
	@RequestMapping(path = "/auth/{email}/{password}", method = RequestMethod.POST)
	public ResponseEntity<String> auth(@PathVariable String email, @PathVariable String password) {
		Optional<Customer> obj = customerRepository.findByEmailAndPassword(email, password);
		
		if (obj.isPresent())
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		else
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);		
	}	
	
}
