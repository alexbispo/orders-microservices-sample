package br.com.alexbispo.orders.creation;

public final class OrderCreationResponseModel {
	private final Long id;

	public OrderCreationResponseModel(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "OrderResponseModel [id=" + id + "]";
	}
	
}
