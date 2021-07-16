package br.com.alexbispo.orders.entities;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Order {

	private final UUID id;
	private final Set<OrderItem> orderItems;
	private final BigDecimal amount;
	
	private Order(UUID id, Set<OrderItem> items, BigDecimal amount) {
		this.id = id;
		this.orderItems = items;
		this.amount = amount;
	}
	
	public Order() {
		this(null, new HashSet<>(), BigDecimal.ZERO);
	}

	public UUID getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Set<OrderItem> getOrderItems() {
		return Collections.unmodifiableSet(orderItems);
	}

	public Order setId(UUID id) {
		return new Order(id, this.orderItems, this.amount);
	}

	public Order addItems(Set<OrderItem> items) {
		HashSet<OrderItem> newOrderItems = new HashSet<>(this.orderItems);

		BigDecimal newAmount = this.amount;
		if (newOrderItems.addAll(items)) {
			newAmount = calculateAmount(newOrderItems);
		}
		
		return new Order(this.id, newOrderItems, newAmount);
	}

	public Order addItem(OrderItem item) {
		HashSet<OrderItem> orderItems = new HashSet<>(this.orderItems);
		
		BigDecimal newAmount = this.amount;
		if (orderItems.add(item)) {
			newAmount = calculateAmount(orderItems);
		}
		
		return new Order(this.id, orderItems, newAmount);
	}

	public boolean isAmount(BigDecimal bigDecimal) {
		return this.amount.compareTo(bigDecimal) == 0;
	}
	
	private BigDecimal calculateAmount(Set<OrderItem> items) {
		return items.stream().reduce(BigDecimal.ZERO, (acc, el) -> 
					acc.add(el.getAmount()), BigDecimal::add);

	}

}
