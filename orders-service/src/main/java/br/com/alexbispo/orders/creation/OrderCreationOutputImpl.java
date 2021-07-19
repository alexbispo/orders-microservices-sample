package br.com.alexbispo.orders.creation;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderCreationOutputImpl implements OrderCreationOutput {

	@Override
	public Optional<OrderCreationResponseModel> success() {
		return Optional.empty();
	}

	@Override
	public Optional<OrderCreationResponseModel> fail(String message) {
		throw new RuntimeException(message);
	}

}
