package br.com.alexbispo.orders.creation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import br.com.alexbispo.orders.creation.repository.OrderCreationOrdersRepository;
import br.com.alexbispo.orders.creation.repository.OrderCreationProductsRepository;
import br.com.alexbispo.orders.creation.repository.OrderCreationUsersRepository;
import br.com.alexbispo.orders.entity.Product;
import br.com.alexbispo.orders.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

	@Autowired
	private OrderCreationUsersRepository usersRepository;

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
	void givenAnOrderWithProductThatDoesNotExists_whenCreate_thenThrowsException() {
		User savedUser = usersRepository.save(new User());
		Set<OrderCreationItemRequestModel> items = new HashSet<OrderCreationItemRequestModel>();
		items.add(new OrderCreationItemRequestModel(UUID.randomUUID(), 10L));

		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
				savedUser.getId(),
			new BigDecimal("10.00"),
			items
		);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			orderCreationInput.create(requestModel);
		});

		assertEquals("One or more products not found.", exception.getMessage());
	}

	@Test
	void givenAnOrderWithOrderItemThatQuantityIsNotAvailable_whenCreate_thenThrowsException() {
		User savedUser = usersRepository.save(new User());

		Product soldOutProduct = orderProductsRepo.save(
				new Product(new BigDecimal("50.00"), 0));

		Set<OrderCreationItemRequestModel> items = new HashSet<OrderCreationItemRequestModel>();
		items.add(new OrderCreationItemRequestModel(soldOutProduct.getId(), 1L));

		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
				savedUser.getId(),
			new BigDecimal("50.00"),
			items
		);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			 this.orderCreationInput.create(requestModel);
		});

		assertEquals("Available quantity sold out. " + soldOutProduct, exception.getMessage());
	}

	@Test
	void givenAnOrderWithIncorrectAmount_whenCreate_thenThrowsException() {
		User savedUser = usersRepository.save(new User());
		Product availableProduct = orderProductsRepo.save(
				new Product(new BigDecimal("50.00"), 100));

		Set<OrderCreationItemRequestModel> requestedItems = new HashSet<OrderCreationItemRequestModel>();
		requestedItems.add(new OrderCreationItemRequestModel(availableProduct.getId(), 10L));

		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
				savedUser.getId(),
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
		User savedUser = usersRepository.save(new User());
		Product availableProduct = orderProductsRepo.save(
				new Product(new BigDecimal("55.00"), 100L));
		Set<OrderCreationItemRequestModel> requestedItems = new HashSet<OrderCreationItemRequestModel>();
		requestedItems.add(new OrderCreationItemRequestModel(availableProduct.getId(), 2L));

		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
				savedUser.getId(),
			new BigDecimal("110.00"),
			requestedItems
		);

		Optional<OrderCreationResponseModel> responseModel = orderCreationInput.create(requestModel);

		assertTrue(responseModel.flatMap(resp -> ordersRepository.findById(resp.getOrderId())).isPresent());
		assertEquals(98L, orderProductsRepo.findById(availableProduct.getId()).orElseThrow().getAvailableQuantity());
	}
	
}
