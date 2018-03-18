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

import br.com.bigdelivery.model.Cousine;
import br.com.bigdelivery.model.Store;
import br.com.bigdelivery.repo.CousineRepository;

@RestController
@RequestMapping("/api/v1/Cousine")
public class CousineController {

	private final CousineRepository cousineRepository;

	@Autowired
	CousineController(CousineRepository cousineRepository) {
		this.cousineRepository = cousineRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Cousine>> listCousines() {
		Iterable<Cousine> lista = cousineRepository.findAll();		
		return new ResponseEntity<Iterable<Cousine>>(lista, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/search/{searchText}", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Cousine>> searchCousines(@PathVariable(value = "searchText") String searchText) {
		Iterable<Cousine> lista = cousineRepository.findByNameStartingWith(searchText);
		return new ResponseEntity<Iterable<Cousine>>(lista, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{cousineId}/stores", method = RequestMethod.GET)
	public ResponseEntity<Set<Store>> listStores(@PathVariable(value = "cousineId") Long cousineId) {
		Optional<Cousine> obj = cousineRepository.findById(cousineId);
		if (obj.isPresent()){
			return new ResponseEntity<Set<Store>>(obj.get().getStores(), HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
}
