package br.com.alexbispo.orders.creation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import br.com.alexbispo.orders.entities.Order;
import br.com.alexbispo.orders.entities.OrderItem;
import br.com.alexbispo.orders.entities.Product;
import org.junit.jupiter.api.BeforeEach;
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
	private OrderCreationProductsRepository orderProductsRepo;

	@Autowired
	private OrderCreationOrdersRepository ordersRepository;

//	@BeforeEach
//	void init() {
//		orderProductsRepo.
//	}

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
		Product soldOutProduct = new Product(
				Optional.of(UUID.randomUUID()),
				Optional.of(new BigDecimal("50.00")), 0
		);
		orderProductsRepo.save(soldOutProduct);

		Set<OrderCreationItemRequestModel> items = new HashSet<OrderCreationItemRequestModel>();
		items.add(new OrderCreationItemRequestModel(soldOutProduct.getId(), 10L));

		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("10.00"),
			items
		);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			 this.orderCreationInput.create(requestModel);
		});

		assertEquals("Available quantity sold out. " + soldOutProduct, exception.getMessage());
	}

	@Test
	void givenAnOrderWithIncorrectAmount_whenCreate_thenThrowsException() {
		Product availableProduct = new Product(
				Optional.of(UUID.randomUUID()),
				Optional.of(new BigDecimal("50.00")), 100
		);
		orderProductsRepo.save(availableProduct);

		Set<OrderCreationItemRequestModel> requestedItems = new HashSet<OrderCreationItemRequestModel>();
		requestedItems.add(new OrderCreationItemRequestModel(availableProduct.getId(), 10L));

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
		Product availableProduct = new Product(
				Optional.of(UUID.randomUUID()),
				Optional.of(new BigDecimal("55.00")), 100L
		);
		orderProductsRepo.save(availableProduct);
		Set<OrderCreationItemRequestModel> requestedItems = new HashSet<OrderCreationItemRequestModel>();
		requestedItems.add(new OrderCreationItemRequestModel(availableProduct.getId(), 2L));

		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("110.00"),
			requestedItems
		);

		Optional<OrderCreationResponseModel> responseModel = orderCreationInput.create(requestModel);

		assertTrue(responseModel.flatMap(resp -> ordersRepository.findById(resp.getId())).isPresent());
	}
	
}
