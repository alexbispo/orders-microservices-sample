package br.com.alexbispo.orders.entities;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public final class OrderItem {
	private final UUID id;
	private final long quantity;
	private final BigDecimal amount;
	private final Product product;

	private OrderItem(UUID id, Product product, long quantity, BigDecimal amount) {
		this.id = id;
		this.quantity = quantity;
		this.amount = amount;
		this.product = product;
	}

	public OrderItem(Optional<Product> product) {
		this(null, product.orElseThrow(), 0L, BigDecimal.ZERO);
	}
	
	public UUID getId() {
		return this.id;
	}

	public OrderItem setId(UUID id) {
		return new OrderItem(id, this.product, this.quantity, this.amount);
	}

	public BigDecimal getAmount() {
		return this.amount;
	}
	
	public long getQuantity() {
		return this.quantity;
	}
	
	public Product getProduct() {
		return this.product;
	}
	
	public OrderItem place(long quantity) {
		long newQuantity = this.quantity + quantity;
		
		if (newQuantity > this.product.getAvailableQuantity()) {
			throw new RuntimeException("Available quantity sold out. " + this);
		}
		
		Product newProduct = this.product.addAvailableQuantity(-quantity);

		BigDecimal newAmount = newProduct.getPrice().multiply(BigDecimal.valueOf(newQuantity));
		
		return new OrderItem(this.id, newProduct, newQuantity, newAmount);
	}
	
	public OrderItem place() {
		return place(1);
	}

	public long getProductAvailableQuantity() {
		return this.product.getAvailableQuantity();
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", quantity=" + quantity + ", amount=" + amount + "]";
	}

}
