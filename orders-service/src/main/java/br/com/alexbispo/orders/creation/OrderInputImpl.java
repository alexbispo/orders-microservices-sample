package br.com.alexbispo.orders.creation;

import java.util.Set;
import java.util.stream.Collectors;

public final class OrderInputImpl implements OrderInput {
	
	private final UsersRepository usersRepository;
	private final OrdersRepository ordersRepository;
	private final OrderOutput outputImpl;
	private final OrderItemsRepository orderItemsRepository;

	public OrderInputImpl(UsersRepository usersRepository, OrdersRepository ordersRepository,
			OrderItemsRepository orderItemsRepository, OrderOutput outputImpl) {
		this.usersRepository = usersRepository;
		this.ordersRepository = ordersRepository;
		this.outputImpl = outputImpl;
		this.orderItemsRepository = orderItemsRepository;
	}

	@Override
	public OrderResponseModel create(OrderRequestModel requestModel) {
		if (!usersRepository.existsById(requestModel.getUserId())) {
			return outputImpl.fail("User not found.");
		}
		
		Set<OrderItem> orderItemsFound = orderItemsRepository.findByIds(requestModel.getOrderItemsIds());
		if (orderItemsFound.isEmpty()) {
			return outputImpl.fail("Order Items not found.");
		}
		
		Set<OrderItem> placedItems = orderItemsFound.stream().map(item -> 
			item.place(requestModel.getRequestedOrderItemForId(item.getId()).getQuantity())
		).collect(Collectors.toSet());
		
		return null;
	}

}
