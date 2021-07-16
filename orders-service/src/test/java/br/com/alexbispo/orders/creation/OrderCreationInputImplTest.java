package br.com.alexbispo.orders.creation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import br.com.alexbispo.orders.entities.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderCreationInputImplTest {
	
	@Autowired
	private OrderCreationInput orderCreationInput;

	@Autowired
	private OrderCreationItemsRepository orderItemsRepo;

	@Autowired
	private OrderCreationOrdersRepository ordersRepository;

	@Test
	void givenAnOrderWithUserThatDoesNotExists_whenCreate_thenThrowsException() {
		Set<OrderCreationItemRequestModel> items = new HashSet<OrderCreationItemRequestModel>();
		items.add(new OrderCreationItemRequestModel(UUID.randomUUID(), 10L));
		
		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("10.00"),
			items
		);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			 orderCreationInput.create(requestModel);
		});
		
		assertEquals("User not found.", exception.getMessage());
	}
	
	@Test
	void givenAnOrderWithOrderItemThatDoesNotExists_whenCreate_thenThrowsException() {
		Set<OrderCreationItemRequestModel> items = new HashSet<OrderCreationItemRequestModel>();
		items.add(new OrderCreationItemRequestModel(UUID.randomUUID(), 10L));

		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("10.00"),
			items
		);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			orderCreationInput.create(requestModel);
		});

		assertEquals("Order Items not found.", exception.getMessage());
	}

	@Test
	void givenAnOrderWithOrderItemThatQuantityIsNotAvailable_whenCreate_thenThrowsException() {
		OrderItem soldOutItem = new OrderItem(UUID.randomUUID(), new BigDecimal("50.00"), 0);
		orderItemsRepo.save(soldOutItem);

		Set<OrderCreationItemRequestModel> items = new HashSet<OrderCreationItemRequestModel>();
		items.add(new OrderCreationItemRequestModel(soldOutItem.getId(), 10L));

		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("10.00"),
			items
		);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			 this.orderCreationInput.create(requestModel);
		});

		assertEquals("Available quantity sold out. " + soldOutItem, exception.getMessage());
	}

	@Test
	void givenAnOrderWithIncorrectAmount_whenCreate_thenThrowsException() {
		OrderItem availableItem = new OrderItem(UUID.randomUUID(), new BigDecimal("50.00"), 100);
		orderItemsRepo.save(availableItem);

		Set<OrderCreationItemRequestModel> requestedItems = new HashSet<OrderCreationItemRequestModel>();
		requestedItems.add(new OrderCreationItemRequestModel(availableItem.getId(), 10L));

		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("10.00"),
			requestedItems
		);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			 orderCreationInput.create(requestModel);
		});

		assertEquals("Invalid Order amount.", exception.getMessage());
	}

	@Test
	void givenTheValidOrderRequest_whenCreate_thenSaveOrder() {
		OrderCreationOutput outputImpl = new OrderCreationOutputImpl();
		OrderItem availableItem = new OrderItem(UUID.randomUUID(), new BigDecimal("55.00"), 100L);
		orderItemsRepo.save(availableItem);
		Set<OrderCreationItemRequestModel> requestedItems = new HashSet<OrderCreationItemRequestModel>();
		requestedItems.add(new OrderCreationItemRequestModel(availableItem.getId(), 2L));

		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("110.00"),
			requestedItems
		);

		OrderCreationResponseModel responseModel = orderCreationInput.create(requestModel);

		assertTrue(ordersRepository.findById(responseModel.getId()).isPresent());
	}
	
}
