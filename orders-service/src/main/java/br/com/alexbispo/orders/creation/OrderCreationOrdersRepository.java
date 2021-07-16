package br.com.alexbispo.orders.creation;

import br.com.alexbispo.orders.entities.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderCreationOrdersRepository {
	
	void save(Order order);

	Optional<Order> findById(UUID id);
}
