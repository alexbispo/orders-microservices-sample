package br.com.alexbispo.orders.creation;

import br.com.alexbispo.orders.entities.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderCreationOrdersRepository {
	
	Optional<Order> save(Order order);

	Optional<Order> findById(UUID id);
}
