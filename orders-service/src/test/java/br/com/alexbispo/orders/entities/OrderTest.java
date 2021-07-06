package br.com.alexbispo.orders.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.com.alexbispo.orders.entities.Order;

public class OrderTest {
	
	@Test
	void givenOrderItemsAndAmount_whenAmountIsDifferent_thenIsFalse() {
		OrderItem item1 = new OrderItem(UUID.randomUUID(), new BigDecimal("50.00"), 10L).place();
		OrderItem item2 = new OrderItem(UUID.randomUUID(), new BigDecimal("50.00"), 10L).place();
		
		Order order = new Order().addItem(item1).addItem(item2);
		
		assertFalse(order.isAmount(new BigDecimal("10.00")));
	}
	
	@Test
	void givenOrderItemsAndAmount_whenAmountIsCorrect_thenIsTrue() {
		OrderItem item1 = new OrderItem(UUID.randomUUID(), new BigDecimal("50.00"), 10L).place();
		OrderItem item2 = new OrderItem(UUID.randomUUID(), new BigDecimal("50.00"), 10L).place(2);
		
		Set<OrderItem> items = new HashSet<>();
		items.add(item1);
		items.add(item2);
		Order order = new Order().addItems(items);
		
		assertTrue(order.isAmount(new BigDecimal("150.00")));
	}
	
}
