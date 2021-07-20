package br.com.alexbispo.orders.entities;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public final class OrderItem {
	private final UUID id;
	private final long quantity;
	private final BigDecimal amount;
	private final Product product;

	private OrderItem(UUID id, Optional<Product> product, long quantity, BigDecimal amount) {
		this.id = id;
		this.quantity = quantity;
		this.amount = amount;
		this.product = product.orElseThrow();
	}

	public OrderItem(Product product) {
		this(null, Optional.ofNullable(product), 0L, BigDecimal.ZERO);
	}
	
	public UUID getId() {
		return this.id;
	}

	public OrderItem setId(UUID id) {
		return new OrderItem(id, Optional.ofNullable(this.product), this.quantity, this.amount);
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
			throw new RuntimeException("Available quantity sold out. " + this.product);
		}
		
		Product newProduct = this.product.addAvailableQuantity(-quantity);

		BigDecimal newAmount = newProduct.getPrice().multiply(BigDecimal.valueOf(newQuantity));
		
		return new OrderItem(this.id, Optional.ofNullable(newProduct), newQuantity, newAmount);
	}
	
	public OrderItem place() {
		return place(1);
	}

	public long getProductAvailableQuantity() {
		return this.product.getAvailableQuantity();
	}

	public BigDecimal getProductPrice() {
		return this.product.getPrice();
	}

	public UUID getProductId() {
		return this.product.getId();
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", quantity=" + quantity + ", amount=" + amount + "]";
	}

}
