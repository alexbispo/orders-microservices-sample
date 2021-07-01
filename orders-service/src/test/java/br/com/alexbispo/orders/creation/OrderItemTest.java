package br.com.alexbispo.orders.creation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class OrderItemTest {
	
	@Test
	void whenIncrement_thenAmountShouldBeCalculated() {
		OrderItem item = new OrderItem(UUID.randomUUID(), new BigDecimal("1.99"), 20L).place(10);
		
		BigDecimal expected = new BigDecimal("19.90");
		assertTrue(expected.compareTo(item.getAmount()) == 0);
	}
	
	@Test
	void whenIncrement_thenDecreaseAvailableQuantity() {
		OrderItem item = new OrderItem(UUID.randomUUID(), new BigDecimal("1.99"), 20L).place(10);
		
		assertEquals(10L, item.getAvailableQuantity());
	}
	
	@Test
	void whenIncrementToGreaterThanAvailableQuantity_thenThrowsException() {
		UUID itemId = UUID.randomUUID();
		long availableQuantity = 5L;
		OrderItem item = new OrderItem(itemId, new BigDecimal("1.99"), availableQuantity);
		
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			item.place(10);
		});
		
		assertEquals(
				"Available quantity sold out. " + item, 
				exception.getMessage()
		);
	}
	
}
