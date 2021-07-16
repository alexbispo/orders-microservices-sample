package br.com.alexbispo.orders.creation;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.alexbispo.orders.entities.Order;
import br.com.alexbispo.orders.entities.OrderItem;

@Component
public final class OrderCreationInputImpl implements OrderCreationInput {
	
	private final OrderCreationUsersRepository usersRepository;
	private final OrderCreationOrdersRepository ordersRepository;
	private final OrderCreationOutput outputImpl;
	private final OrderCreationItemsRepository orderItemsRepository;

	public OrderCreationInputImpl(OrderCreationUsersRepository usersRepository, OrderCreationOrdersRepository ordersRepository,
								  OrderCreationItemsRepository orderItemsRepository, OrderCreationOutput outputImpl) {
		this.usersRepository = usersRepository;
		this.ordersRepository = ordersRepository;
		this.outputImpl = outputImpl;
		this.orderItemsRepository = orderItemsRepository;
	}

	@Override
	public OrderCreationResponseModel create(OrderCreationRequestModel requestModel) {
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
		
		Order order = new Order().addItems(placedItems);
		
		if(!order.isAmount(requestModel.getAmount())) {
			return outputImpl.fail("Invalid Order amount.");
		}
		
		ordersRepository.save(order);
		
		return null;
	}

}
