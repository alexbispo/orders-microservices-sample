package br.com.alexbispo.orders.creation;

import java.math.BigDecimal;
import java.util.UUID;

public final class OrderItem {
	private final UUID id;
	private final long quantity;
	private final BigDecimal amount;
	private final BigDecimal price;
	private final long availableQuanity;

	private OrderItem(UUID id, BigDecimal price, long availableQuanity, long quantity, BigDecimal amount) {
		this.id = id;
		this.quantity = quantity;
		this.amount = amount;
		this.price = price;
		this.availableQuanity = availableQuanity;
	}

	public OrderItem(UUID id, BigDecimal price, long availableQuanity) {
		this(id, price, availableQuanity, 0L, BigDecimal.ZERO);
	}
	
	public UUID getId() {
		return this.id;
	}
	
	public BigDecimal getAmount() {
		return this.amount;
	}
	
	public long getQuantity() {
		return this.quantity;
	}
	
	public long getAvailableQuantity() {
		return this.availableQuanity;
	}
	
	public OrderItem place(long quantity) {
		long newQuantity = this.quantity + quantity;
		
		if (newQuantity > this.availableQuanity) {
			throw new RuntimeException("Available quantity sold out. " + this);
		}
		
		long newAvailableQuantity = this.availableQuanity - quantity; 
		
		BigDecimal newAmount = this.price.multiply(BigDecimal.valueOf(newQuantity));
		
		return new OrderItem(this.id, this.price, newAvailableQuantity, newQuantity, newAmount);
	}
	
	public OrderItem place() {
		return place(1);
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", quantity=" + quantity + ", amount=" + amount + ", price=" + price
				+ ", availableQuanity=" + availableQuanity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
