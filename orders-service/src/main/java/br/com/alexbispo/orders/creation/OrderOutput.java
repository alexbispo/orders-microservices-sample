package br.com.alexbispo.orders.creation;

public interface OrderOutput {
	public OrderResponseModel success();
	
	public OrderResponseModel fail(String message);
}
