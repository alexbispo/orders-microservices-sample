package br.com.alexbispo.orders.creation;

import br.com.alexbispo.orders.entities.Order;
import br.com.alexbispo.orders.entities.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaOrderCreationOrders implements OrderCreationOrdersRepository{

    private final JpaOrderCreationOrdersRepository repo;

    public JpaOrderCreationOrders(JpaOrderCreationOrdersRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(Order order) {
    }

    @Override
    public Optional<Order> findById(UUID id) {
        Optional<JpaOrder> foundOrder = repo.findById(id);

        Optional<Order> result = foundOrder.flatMap(fo -> {
            Set<OrderItem> items = fo.getItems().stream()
                    .map(it ->
                            new OrderItem(
                                    it.getId(),
                                    it.getPrice(),
                                    it.getAvailableQuantity()).place(it.getQuantity()))
                    .collect(Collectors.toSet());
            return Optional.of(new Order().setId(fo.getId()).addItems(items));
        });

        return result;
    }
}
