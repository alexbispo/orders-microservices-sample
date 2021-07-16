package br.com.alexbispo.orders.creation;

import org.springframework.stereotype.Component;

@Component
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
