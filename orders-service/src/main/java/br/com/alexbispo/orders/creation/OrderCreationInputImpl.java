package br.com.alexbispo.orders.creation;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.alexbispo.orders.entities.Product;
import org.springframework.stereotype.Component;

import br.com.alexbispo.orders.entities.Order;
import br.com.alexbispo.orders.entities.OrderItem;

@Component
public final class OrderCreationInputImpl implements OrderCreationInput {
	
	private final OrderCreationUsersRepository usersRepository;
	private final OrderCreationOrdersRepository ordersRepository;
	private final OrderCreationOutput outputImpl;
	private final OrderCreationProductsRepository orderProductsRepository;
	private final OrderCreationItemsRepository itemsRepository;

	public OrderCreationInputImpl(OrderCreationUsersRepository usersRepository, OrderCreationOrdersRepository ordersRepository,
								  OrderCreationProductsRepository orderProductsRepository, OrderCreationOutput outputImpl, OrderCreationItemsRepository itemsRepository) {
		this.usersRepository = usersRepository;
		this.ordersRepository = ordersRepository;
		this.outputImpl = outputImpl;
		this.orderProductsRepository = orderProductsRepository;
		this.itemsRepository = itemsRepository;
	}

	@Override
	public Optional<OrderCreationResponseModel> create(OrderCreationRequestModel requestModel) {
		if (!usersRepository.existsById(requestModel.getUserId())) {
			return outputImpl.fail("User not found.");
		}
		
		Set<Product> productsFound = this.orderProductsRepository.findByIds(requestModel.getOrderItemsIds());
		if (productsFound.size() != requestModel.getOrderItemsIds().size()) {
			return outputImpl.fail("One or more products not found.");
		}
		
		Set<OrderItem> placedItems = productsFound.stream().map(product -> {
		    return new OrderItem(product)
                    .place(requestModel.getRequestedOrderItemForId(product.getId()).getQuantity());
        }).collect(Collectors.toSet());
		
		Order order = new Order().addItems(placedItems);
		
		if(!order.isAmount(requestModel.getAmount())) {
			return outputImpl.fail("Invalid Order amount.");
		}

		Set<Product> changedProducts = placedItems.stream().map(it -> it.getProduct()).collect(Collectors.toSet());

		Order savedOder = ordersRepository.save(order);
		itemsRepository.saveAll(placedItems, savedOder.getId());
		orderProductsRepository.saveAll(changedProducts);

		return Optional.ofNullable(new OrderCreationResponseModel(savedOder.getId()));
	}

}
