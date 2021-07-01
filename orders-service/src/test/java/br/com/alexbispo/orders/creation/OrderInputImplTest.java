package br.com.alexbispo.orders.creation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class OrderInputImplTest {
	
	final class FakeUsersRepository implements UsersRepository {
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
	
	final class FakeOrdersRepository implements OrdersRepository {

		@Override
		public void save(OrdersRepositoryRequestModel requestModel) {}
		
	}
	
	final class FakeOrderItemsRepository implements OrderItemsRepository {
		
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

		public OrderItemsRepository add(OrderItem soldOutItem) {
			HashSet<OrderItem> newOrderItems = new HashSet<>(this.orderItems);
			newOrderItems.add(soldOutItem);
			return new FakeOrderItemsRepository(newOrderItems);
		}
	}
	
	@Test
	void givenAnOrderWithUserThatDoesNotExists_whenCreate_thenThrowsException() {
		UsersRepository usersRepository = new FakeUsersRepository();
		OrdersRepository ordersRepository = new FakeOrdersRepository();
		OrderItemsRepository orderItemsRepository = new FakeOrderItemsRepository();
		OrderOutput outputImpl = new OrderOutputImpl();
		
		OrderInputImpl orderCreateService = new OrderInputImpl(usersRepository, ordersRepository, orderItemsRepository, outputImpl);
		
		Set<OrderItemRequestModel> items = new HashSet<OrderItemRequestModel>();
		items.add(new OrderItemRequestModel(UUID.randomUUID(), 10L));
		
		OrderRequestModel requestModel = new OrderRequestModel(
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
		UsersRepository usersRepository = new FakeUsersRepository().givenThatUserExists();
		OrdersRepository ordersRepository = new FakeOrdersRepository();
		OrderItemsRepository orderItemsRepository = new FakeOrderItemsRepository();
		OrderOutput outputImpl = new OrderOutputImpl();
		
		OrderInputImpl orderCreateService = new OrderInputImpl(usersRepository, ordersRepository, orderItemsRepository, outputImpl);
		
		Set<OrderItemRequestModel> items = new HashSet<OrderItemRequestModel>();
		items.add(new OrderItemRequestModel(UUID.randomUUID(), 10L));
		
		OrderRequestModel requestModel = new OrderRequestModel(
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
		UsersRepository usersRepository = new FakeUsersRepository().givenThatUserExists();
		OrdersRepository ordersRepository = new FakeOrdersRepository();
		OrderOutput outputImpl = new OrderOutputImpl();
		
		OrderItem soldOutItem = new OrderItem(UUID.randomUUID(), new BigDecimal("50.00"), 0);
		OrderItemsRepository orderItemsRepository = new FakeOrderItemsRepository()
				.add(soldOutItem);
		
		
		OrderInputImpl orderCreateService = new OrderInputImpl(usersRepository, ordersRepository, orderItemsRepository, outputImpl);
		
		Set<OrderItemRequestModel> items = new HashSet<OrderItemRequestModel>();
		items.add(new OrderItemRequestModel(soldOutItem.getId(), 10L));
		
		OrderRequestModel requestModel = new OrderRequestModel(
			UUID.randomUUID(),
			new BigDecimal("10.00"),
			items
		);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			 orderCreateService.create(requestModel);
		});
		
		assertEquals("Available quantity sold out. " + soldOutItem, exception.getMessage());
	}
	
}
