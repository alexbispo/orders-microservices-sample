package br.com.alexbispo.orders.creation;

import br.com.alexbispo.orders.entities.Order;
import br.com.alexbispo.orders.entities.OrderItem;
import br.com.alexbispo.orders.entities.Product;
import org.springframework.stereotype.Component;

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
    public Optional<Order> save(Order order) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        Optional<JpaOrder> foundOrder = repo.findById(id);

        Optional<Order> result = foundOrder.flatMap(fo -> {
            Set<OrderItem> items = fo.getItems().stream()
                    .map(it -> {
                        Product product = new Product(
                                Optional.of(it.getProductId()),
                                Optional.of(it.getProductPrice()),
                                it.getProductAvailableQuantity()
                        );
                        return new OrderItem(Optional.of(product))
                                .place(it.getQuantity())
                                .setId(it.getId());
                    })
                    .collect(Collectors.toSet());
            return Optional.of(new Order().setId(fo.getId()).addItems(items));
        });

        return result;
    }
}
