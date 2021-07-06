package br.com.alexbispo.orders.creation;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class OrderCreationRequestModel {
	private final UUID userId;
	private final BigDecimal amount;
	private final Set<OrderCreationItemRequestModel> orderItems;
	private final Map<UUID, OrderCreationItemRequestModel> requestedQuantityForItem;
	
	public OrderCreationRequestModel(UUID userId, BigDecimal amount, Set<OrderCreationItemRequestModel> orderItems) {
		this.userId = userId;
		this.amount = amount;
		this.orderItems = orderItems;
		requestedQuantityForItem = new HashMap<>();
	}

	public UUID getUserId() {
		return this.userId;
	}
	
	public BigDecimal getAmount() {
		return this.amount;
	}
	
	public Set<OrderCreationItemRequestModel> getOrderItems() {
		return Collections.unmodifiableSet(this.orderItems);
	}
	
	public Set<UUID> getOrderItemsIds() {
		return getOrderItems().stream().map(OrderCreationItemRequestModel::getOrderIemId).collect(Collectors.toSet());
	}
	
	public OrderCreationItemRequestModel getRequestedOrderItemForId(UUID orderItemId) {
		if (!requestedQuantityForItem.isEmpty()) {
			return requestedQuantityForItem.get(orderItemId);
		}
		
		getOrderItems().forEach(item -> {
			requestedQuantityForItem.put(item.getOrderIemId(), item);
		});
		
		return requestedQuantityForItem.get(orderItemId);
	}

	@Override
	public String toString() {
		return "OrderRequestModel [userId=" + userId + ", amount=" + amount + ", orderItems=" + orderItems + "]";
	}
	
}
