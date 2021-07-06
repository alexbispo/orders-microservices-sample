package br.com.alexbispo.orders.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public final class Order {
	
	private final Set<OrderItem> orderItems;
	private final BigDecimal amount;
	
	private Order(Set<OrderItem> items, BigDecimal amount) {
		this.orderItems = items;
		this.amount = amount;
	}
	
	public Order() {
		this(new HashSet<>(), BigDecimal.ZERO);
	}
	
	public Order addItems(Set<OrderItem> items) {
		HashSet<OrderItem> newOrderItems = new HashSet<>(this.orderItems);

		BigDecimal newAmount = this.amount;
		if (newOrderItems.addAll(items)) {
			newAmount = calculateAmount(newOrderItems);
		}
		
		return new Order(newOrderItems, newAmount);
	}

	public Order addItem(OrderItem item) {
		HashSet<OrderItem> orderItems = new HashSet<>(this.orderItems);
		
		BigDecimal newAmount = this.amount;
		if (orderItems.add(item)) {
			newAmount = calculateAmount(orderItems);
		}
		
		return new Order(orderItems, newAmount);
	}

	public boolean isAmount(BigDecimal bigDecimal) {
		return this.amount.compareTo(bigDecimal) == 0;
	}
	
	private BigDecimal calculateAmount(Set<OrderItem> items) {
		return items.stream().reduce(BigDecimal.ZERO, (acc, el) -> 
					acc.add(el.getAmount()), BigDecimal::add);

	}

}
