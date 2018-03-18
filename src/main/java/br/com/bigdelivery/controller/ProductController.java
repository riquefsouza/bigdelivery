package br.com.bigdelivery.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.bigdelivery.model.Product;
import br.com.bigdelivery.repo.ProductRepository;

@RestController
@RequestMapping("/api/v1/Product")
public class ProductController {

	private final ProductRepository productRepository;

	@Autowired
	ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> listProducts() {
		Iterable<Product> lista = productRepository.findAll();
		return new ResponseEntity<Iterable<Product>>(lista, HttpStatus.OK);
	}

	@RequestMapping(path = "/search/{searchText}", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> searchProducts(@PathVariable(value = "searchText") String searchText) {
		Iterable<Product> lista = productRepository.findByDescriptionStartingWith(searchText);
		return new ResponseEntity<Iterable<Product>>(lista, HttpStatus.OK);
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable(value = "productId") Long productId) {
		Optional<Product> obj = productRepository.findById(productId);
		if (obj.isPresent())
			return new ResponseEntity<Product>(obj.get(), HttpStatus.OK);
		else
			return ResponseEntity.notFound().build();
	}

}
