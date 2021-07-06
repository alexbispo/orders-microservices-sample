package br.com.alexbispo.orders.creation;

import java.util.UUID;

public final class OrderCreationItemRequestModel {
	
	private final UUID orderItemId;
	private final long quantity;
	
	public OrderCreationItemRequestModel(UUID orderItemId, long quantity) {
		this.orderItemId = orderItemId;
		this.quantity = quantity;
	}
	
	public UUID getOrderIemId() {
		return this.orderItemId;
	}
	
	public long getQuantity() {
		return this.quantity;
	}

	@Override
	public String toString() {
		return "OrderItemRequestModel [orderItemId=" + orderItemId + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderItemId == null) ? 0 : orderItemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderCreationItemRequestModel other = (OrderCreationItemRequestModel) obj;
		if (orderItemId == null) {
			if (other.orderItemId != null)
				return false;
		} else if (!orderItemId.equals(other.orderItemId))
			return false;
		return true;
	}
}
