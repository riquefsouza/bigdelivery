package br.com.bigdelivery.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.bigdelivery.model.Product;
import br.com.bigdelivery.model.Store;
import br.com.bigdelivery.repo.StoreRepository;

@RestController
@RequestMapping("/api/v1/Store")
public class StoreController {

	private final StoreRepository storeRepository;

	@Autowired
	StoreController(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Store>> listStores() {
		Iterable<Store> lista = storeRepository.findAll();
		return new ResponseEntity<Iterable<Store>>(lista, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/search/{searchText}", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Store>> searchStores(@PathVariable(value = "searchText") String searchText) {
		Iterable<Store> lista = storeRepository.findByNameStartingWith(searchText);
		return new ResponseEntity<Iterable<Store>>(lista, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{storeId}", method = RequestMethod.GET)
	public ResponseEntity<Store> getProduct(@PathVariable(value = "storeId") Long storeId) {
		Optional<Store> obj = storeRepository.findById(storeId);
		if (obj.isPresent())
			return new ResponseEntity<Store>(obj.get(), HttpStatus.OK);
		else
			return ResponseEntity.notFound().build();
	}
	
	@RequestMapping(value = "/{storeId}/products", method = RequestMethod.GET)
	public ResponseEntity<Set<Product>> listProducts(@PathVariable(value = "storeId") Long storeId) {
		Optional<Store> obj = storeRepository.findById(storeId);
		if (obj.isPresent()){
			return new ResponseEntity<Set<Product>>(obj.get().getProducts(), HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
}
