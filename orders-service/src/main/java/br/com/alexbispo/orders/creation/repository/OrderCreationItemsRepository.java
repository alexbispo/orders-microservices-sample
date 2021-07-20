package br.com.alexbispo.orders.creation.repository;

import java.util.Set;
import java.util.UUID;

import br.com.alexbispo.orders.entity.OrderItem;

public interface OrderCreationItemsRepository {
	
	public Set<OrderItem> findByIds(Set<UUID> ids);

	void saveAll(Set<OrderItem> orderItems, UUID orderId);
}
