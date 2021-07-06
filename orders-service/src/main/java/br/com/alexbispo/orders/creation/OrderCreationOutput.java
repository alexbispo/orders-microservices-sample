package br.com.alexbispo.orders.creation;

public interface OrderCreationOutput {
	public OrderCreationResponseModel success();
	
	public OrderCreationResponseModel fail(String message);
}
