package br.com.alexbispo.orders.creation;

import br.com.alexbispo.orders.entities.Order;
import br.com.alexbispo.orders.entities.OrderItem;
import br.com.alexbispo.orders.entities.Product;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaOrderCreationOrders implements OrderCreationOrdersRepository{

    private final JpaOrderCreationOrdersRepository repo;

    private final EntityManager entityManager;

    public JpaOrderCreationOrders(JpaOrderCreationOrdersRepository repo, EntityManager entityManager) {
        this.repo = repo;
        this.entityManager = entityManager;
    }

    @Override
    public Order save(Order order) {
        JpaOrder jpaOrder = new JpaOrder();
        jpaOrder.setAmount(order.getAmount());
        JpaOrder savedOrder = this.repo.saveAndFlush(jpaOrder);
        return order.setId(savedOrder.getId());
    }

    @Override
    public Optional<Order> findById(UUID id) {
        Optional<JpaOrder> foundOrder = repo.findById(id);

        Optional<Order> result = foundOrder.flatMap(fo -> {
            Set<OrderItem> items = fo.getItems().stream()
                    .map(it -> {
                        Product product = new Product(
                                it.getProductPrice(),
                                it.getProductAvailableQuantity()
                        ).setId(it.getProductId());

                        return new OrderItem(product)
                                .place(it.getQuantity())
                                .setId(it.getId());
                    })
                    .collect(Collectors.toSet());
            return Optional.of(new Order().setId(fo.getId()).addItems(items));
        });

        return result;
    }
}
