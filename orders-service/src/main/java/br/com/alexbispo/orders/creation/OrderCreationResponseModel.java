package br.com.alexbispo.orders.creation;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class OrderCreationResponseModel {
	private final UUID orderId;

	private OrderCreationResponseModel(Optional<UUID> id) {
		this.orderId = id.orElseThrow();
	}

	public OrderCreationResponseModel(UUID id) {
		this(Optional.ofNullable(id));
	}

	public UUID getOrderId() {
		return orderId;
	}

	@Override
	public String toString() {
		return "OrderResponseModel [id=" + orderId + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderCreationResponseModel that = (OrderCreationResponseModel) o;
		return orderId.equals(that.orderId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}
}
