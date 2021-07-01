package br.com.alexbispo.orders.creation;

public class OrderOutputImpl implements OrderOutput {

	@Override
	public OrderResponseModel success() {
		return null;
	}

	@Override
	public OrderResponseModel fail(String message) {
		throw new RuntimeException(message);
	}

}
