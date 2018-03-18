package br.com.bigdelivery.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import br.com.bigdelivery.model.Order;
import br.com.bigdelivery.model.OrderItem;
import br.com.bigdelivery.repo.OrderItemRepository;
import br.com.bigdelivery.repo.OrderRepository;

@RestController
@RequestMapping("/api/v1/Order")
public class OrderController {
	
	private final OrderRepository orderRepository;
	
	private final OrderItemRepository orderItemRepository;
	
	@Autowired
	OrderController(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
		this.orderRepository = orderRepository;
		this.orderItemRepository= orderItemRepository;
	}
	
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<Order> getOrder(@PathVariable(value = "orderId") Long orderId) {
		Optional<Order> obj = orderRepository.findById(orderId);   	
		if (obj.isPresent())
			return new ResponseEntity<Order>(obj.get(), HttpStatus.OK);
		else
			return ResponseEntity.notFound().build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> postOrder(@RequestBody Order input){
		
		Order obj = new Order(input.getContact(),
				input.getDeliveryAddress(), input.getStatus(), input.getCustomer(), input.getStore());
		
		BigDecimal total = BigDecimal.ZERO;

		for (OrderItem item : obj.getOrderItems()) {
			total = total.add(item.getTotal());
		}
		obj.setTotal(total);
		
		Order result = orderRepository.save(obj);
		
		if (result!=null)
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		else
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);	
	}
	
	@RequestMapping(path = "/customer", method = RequestMethod.GET)
	public ResponseEntity<List<Customer>> getCustomer() {
		Iterable<Order> obj = orderRepository.findAll();
		List<Order> listaOrder = new ArrayList<>();
		obj.forEach(item -> listaOrder.add(item));
		
		List<Customer> lista = new ArrayList<>();
		for (Order order : listaOrder) {
			lista.add(order.getCustomer());
		}
		
		return new ResponseEntity<List<Customer>>(lista, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cancelStatus/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<String> cancelOrder(@PathVariable(value = "orderId") Long orderId) {
		Optional<Order> obj = orderRepository.findById(orderId);   	

		obj.get().setStatus("CANCEL");
				
		if (obj.isPresent()) {
			Order result = orderRepository.save(obj.get());
			if (result!=null) {
				return new ResponseEntity<String>("Success", HttpStatus.OK);
			}
		} 
			
		return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);	
	}
	
	@RequestMapping(value = "/cancel/{orderId}", method = RequestMethod.DELETE)
	public ResponseEntity<Order> deleteOrder(@PathVariable(value = "orderId") Long orderId) {
		Optional<Order> obj = orderRepository.findById(orderId);   	
		
		if (!obj.isPresent()) {
			System.out.println("Unable to delete. Order with id " + orderId + " not found");
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
		
		for (OrderItem item : obj.get().getOrderItems()) {
			orderItemRepository.deleteById(item.getId());
		}
		
		orderRepository.deleteById(orderId);			
		return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
	}	
	
	@RequestMapping(value = "/status/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStatusOrder(@PathVariable(value = "orderId") Long orderId) {
		Optional<Order> obj = orderRepository.findById(orderId);  
		if (obj.isPresent()) 
			return new ResponseEntity<String>(obj.get().getStatus(), HttpStatus.OK);
		else
			return new ResponseEntity<String>("Failure", HttpStatus.NOT_FOUND);
	}	
}
