package br.com.alexbispo.orders.creation;

import java.util.Optional;

public interface OrderCreationOutput {
	public Optional<OrderCreationResponseModel> success();
	
	public Optional<OrderCreationResponseModel> fail(String message);
}
