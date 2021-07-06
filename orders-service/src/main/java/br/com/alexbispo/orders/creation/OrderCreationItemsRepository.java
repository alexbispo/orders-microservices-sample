package br.com.alexbispo.orders.creation;

import java.util.Set;
import java.util.UUID;

import br.com.alexbispo.orders.entities.OrderItem;

public interface OrderCreationItemsRepository {
	
	public Set<OrderItem> findByIds(Set<UUID> ids);
}
