package br.com.alexbispo.orders.creation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

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
		OrderItem item2 = new OrderItem(UUID.randomUUID(), new BigDecimal("50.00"), 10L)
			.place()
			.place();
		
		Order order = new Order().addItem(item1).addItem(item2);
		
		assertTrue(order.isAmount(new BigDecimal("150.00")));
	}
	
}
