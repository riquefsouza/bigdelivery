package br.com.bigdelivery.repo;

import org.springframework.data.repository.CrudRepository;

import br.com.bigdelivery.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
