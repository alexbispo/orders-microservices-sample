package br.com.alexbispo.orders.creation;

public final class OrderResponseModel {
	private final Long id;

	public OrderResponseModel(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "OrderResponseModel [id=" + id + "]";
	}
	
}
