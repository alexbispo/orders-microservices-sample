package br.com.alexbispo.orders.creation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.com.alexbispo.orders.entities.OrderItem;

public class OrderCreationServiceTest {
	
	final class FakeUsersRepository implements OrderCreationUsersRepository {
		private final boolean userExists;
		
		FakeUsersRepository() {
			this(false);
		}
		private FakeUsersRepository(boolean userExists) {
			this.userExists = userExists;
		}
		
		public FakeUsersRepository givenThatUserExists() {
			return new FakeUsersRepository(true);
		}

		@Override
		public boolean existsById(UUID id) {
			return this.userExists;
		}
	}
	
	final class FakeOrdersRepository implements OrderCreationOrdersRepository {
		
		private boolean saveCalled;
		
		public boolean wasSaveCalled() {
			return this.saveCalled;
		}

		@Override
		public void save(OrderCreationOrdersRepositoryRequestModel requestModel) {
			this.saveCalled = true;
		}
		
	}
	
	final class FakeOrderItemsRepository implements OrderCreationItemsRepository {
		
		private final Set<OrderItem> orderItems;
		
		public FakeOrderItemsRepository() {
			this(Collections.emptySet());
		}
		
		private FakeOrderItemsRepository(Set<OrderItem> orderItems) {
			this.orderItems = orderItems;
		}

		@Override
		public Set<OrderItem> findByIds(Set<UUID> ids) {
			return this.orderItems.stream().filter(item -> ids.contains(item.getId())).collect(Collectors.toSet());
		}

		public OrderCreationItemsRepository add(OrderItem soldOutItem) {
			HashSet<OrderItem> newOrderItems = new HashSet<>(this.orderItems);
			newOrderItems.add(soldOutItem);
			return new FakeOrderItemsRepository(newOrderItems);
		}
	}
	
	@Test
	void givenAnOrderWithUserThatDoesNotExists_whenCreate_thenThrowsException() {
		OrderCreationUsersRepository usersRepository = new FakeUsersRepository();
		OrderCreationOrdersRepository ordersRepository = new FakeOrdersRepository();
		OrderCreationItemsRepository orderItemsRepository = new FakeOrderItemsRepository();
		OrderCreationOutput outputImpl = new OrderCreationOutputImpl();
		
		OrderCreationService orderCreateService = new OrderCreationService(usersRepository, ordersRepository, orderItemsRepository, outputImpl);
		
		Set<OrderCreationItemRequestModel> items = new HashSet<OrderCreationItemRequestModel>();
		items.add(new OrderCreationItemRequestModel(UUID.randomUUID(), 10L));
		
		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("10.00"),
			items
		);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			 orderCreateService.create(requestModel);
		});
		
		assertEquals("User not found.", exception.getMessage());
	}
	
	@Test
	void givenAnOrderWithOrderItemThatDoesNotExists_whenCreate_thenThrowsException() {
		OrderCreationUsersRepository usersRepository = new FakeUsersRepository().givenThatUserExists();
		OrderCreationOrdersRepository ordersRepository = new FakeOrdersRepository();
		OrderCreationItemsRepository orderItemsRepository = new FakeOrderItemsRepository();
		OrderCreationOutput outputImpl = new OrderCreationOutputImpl();
		
		OrderCreationService orderCreateService = new OrderCreationService(usersRepository, ordersRepository, orderItemsRepository, outputImpl);
		
		Set<OrderCreationItemRequestModel> items = new HashSet<OrderCreationItemRequestModel>();
		items.add(new OrderCreationItemRequestModel(UUID.randomUUID(), 10L));
		
		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("10.00"),
			items
		);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			 orderCreateService.create(requestModel);
		});
		
		assertEquals("Order Items not found.", exception.getMessage());
	}
	
	@Test
	void givenAnOrderWithOrderItemThatQuantityIsNotAvailable_whenCreate_thenThrowsException() {
		OrderCreationUsersRepository usersRepository = new FakeUsersRepository().givenThatUserExists();
		OrderCreationOrdersRepository ordersRepository = new FakeOrdersRepository();
		OrderCreationOutput outputImpl = new OrderCreationOutputImpl();
		
		OrderItem soldOutItem = new OrderItem(UUID.randomUUID(), new BigDecimal("50.00"), 0);
		OrderCreationItemsRepository orderItemsRepository = new FakeOrderItemsRepository()
				.add(soldOutItem);
		
		
		OrderCreationService orderCreateService = new OrderCreationService(usersRepository, ordersRepository, orderItemsRepository, outputImpl);
		
		Set<OrderCreationItemRequestModel> items = new HashSet<OrderCreationItemRequestModel>();
		items.add(new OrderCreationItemRequestModel(soldOutItem.getId(), 10L));
		
		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("10.00"),
			items
		);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			 orderCreateService.create(requestModel);
		});
		
		assertEquals("Available quantity sold out. " + soldOutItem, exception.getMessage());
	}
	
	@Test
	void givenAnOrderWithIncorrectAmount_whenCreate_thenThrowsException() {
		OrderCreationUsersRepository usersRepository = new FakeUsersRepository().givenThatUserExists();
		OrderCreationOrdersRepository ordersRepository = new FakeOrdersRepository();
		OrderCreationOutput outputImpl = new OrderCreationOutputImpl();
		OrderItem availableItem = new OrderItem(UUID.randomUUID(), new BigDecimal("50.00"), 100);
		OrderCreationItemsRepository orderItemsRepository = new FakeOrderItemsRepository()
				.add(availableItem);
		
		Set<OrderCreationItemRequestModel> requestedItems = new HashSet<OrderCreationItemRequestModel>();
		requestedItems.add(new OrderCreationItemRequestModel(availableItem.getId(), 10L));
		
		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("10.00"),
			requestedItems
		);

		OrderCreationService orderCreateService = new OrderCreationService(usersRepository, ordersRepository, orderItemsRepository, outputImpl);
		
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			 orderCreateService.create(requestModel);
		});
		
		assertEquals("Invalid Order amount.", exception.getMessage());
	}
	
	@Test
	void givenTheValidOrderRequest_whenCreate_thenSaveOrder() {
		OrderCreationUsersRepository usersRepository = new FakeUsersRepository().givenThatUserExists();
		OrderCreationOrdersRepository ordersRepository = new FakeOrdersRepository();
		OrderCreationOutput outputImpl = new OrderCreationOutputImpl();
		OrderItem availableItem = new OrderItem(UUID.randomUUID(), new BigDecimal("55.00"), 100L);
		OrderCreationItemsRepository orderItemsRepository = new FakeOrderItemsRepository()
				.add(availableItem);
		
		Set<OrderCreationItemRequestModel> requestedItems = new HashSet<OrderCreationItemRequestModel>();
		requestedItems.add(new OrderCreationItemRequestModel(availableItem.getId(), 2L));
		
		OrderCreationRequestModel requestModel = new OrderCreationRequestModel(
			UUID.randomUUID(),
			new BigDecimal("110.00"),
			requestedItems
		);

		OrderCreationService orderCreateService = new OrderCreationService(usersRepository, ordersRepository, orderItemsRepository, outputImpl);
		
		orderCreateService.create(requestModel);
		
		assertTrue(((FakeOrdersRepository)ordersRepository).wasSaveCalled());
	}
	
}
