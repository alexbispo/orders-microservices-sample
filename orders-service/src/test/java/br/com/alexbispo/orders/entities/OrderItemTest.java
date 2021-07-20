package br.com.alexbispo.orders.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class OrderItemTest {
	
	@Test
	void whenIncrement_thenAmountShouldBeCalculated() {
		Product product = new Product(new BigDecimal("1.99"), 20L);
		OrderItem item = new OrderItem(product).place(10);
		
		BigDecimal expected = new BigDecimal("19.90");
		assertTrue(expected.compareTo(item.getAmount()) == 0);
	}
	
	@Test
	void whenIncrement_thenDecreaseAvailableQuantity() {
		Product product = new Product(new BigDecimal("1.99"), 20L);

		OrderItem item = new OrderItem(product).place(10);
		
		assertEquals(10L, item.getProductAvailableQuantity());
	}
	
	@Test
	void whenIncrementToGreaterThanAvailableQuantity_thenThrowsException() {
		Product product = new Product(new BigDecimal("1.99"), 5L);

		OrderItem item = new OrderItem(product);
		
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			item.place(10);
		});
		
		assertEquals(
				"Available quantity sold out. " + product,
				exception.getMessage()
		);
	}
	
}
