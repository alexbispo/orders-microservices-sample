package br.com.alexbispo.orders.creation;

import java.util.UUID;

public final class OrderCreationItemRequestModel {
	
	private final UUID productId;
	private final long quantity;
	
	public OrderCreationItemRequestModel(UUID productId, long quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public UUID getOrderIemId() {
		return this.productId;
	}
	
	public long getQuantity() {
		return this.quantity;
	}

	@Override
	public String toString() {
		return "OrderItemRequestModel [orderItemId=" + productId + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}
}
