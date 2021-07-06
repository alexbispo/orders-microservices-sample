package br.com.alexbispo.orders.creation;

public class OrderCreationOutputImpl implements OrderCreationOutput {

	@Override
	public OrderCreationResponseModel success() {
		return null;
	}

	@Override
	public OrderCreationResponseModel fail(String message) {
		throw new RuntimeException(message);
	}

}
