package br.com.alexbispo.orders.creation;

import java.util.Objects;
import java.util.UUID;

public final class OrderCreationResponseModel {
	private final UUID id;

	public OrderCreationResponseModel(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public String toString() {
		return "OrderResponseModel [id=" + id + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderCreationResponseModel that = (OrderCreationResponseModel) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
