package br.com.alexbispo.orders.creation;

import java.util.Optional;

public interface OrderCreationInput {
	public Optional<OrderCreationResponseModel> create(OrderCreationRequestModel requestModel);
}
