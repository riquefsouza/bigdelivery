package br.com.bigdelivery.repo;

import org.springframework.data.repository.CrudRepository;

import br.com.bigdelivery.model.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

}
