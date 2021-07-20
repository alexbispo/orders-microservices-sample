package br.com.alexbispo.orders.creation.repository;

import br.com.alexbispo.orders.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderCreationOrdersRepository {
	
	Order save(Order order);

	Optional<Order> findById(UUID id);
}
