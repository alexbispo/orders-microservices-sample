package br.com.alexbispo.orders.creation;

import java.util.Set;
import java.util.UUID;

public interface OrderItemsRepository {
	
	public Set<OrderItem> findByIds(Set<UUID> ids);
}
