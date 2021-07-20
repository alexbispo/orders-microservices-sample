package br.com.alexbispo.orders.creation.jpa;

import br.com.alexbispo.orders.creation.repository.OrderCreationItemsRepository;
import br.com.alexbispo.orders.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaOrderCreationItems implements OrderCreationItemsRepository {

    private final JpaOrderCreationItemsRepository repo;

    public JpaOrderCreationItems(JpaOrderCreationItemsRepository repo) {
        this.repo = repo;
    }

    @Override
    public Set<OrderItem> findByIds(Set<UUID> ids) {
        return null;
    }

    @Override
    public void saveAll(Set<OrderItem> orderItems, UUID orderId) {
        Set<JpaOrderItem> jpaOrderItems = orderItems.stream().map(orderItem -> {
            JpaOrderItem jpaOrderItem = new JpaOrderItem();
            jpaOrderItem.setAmount(orderItem.getAmount());
            jpaOrderItem.setQuantity(orderItem.getQuantity());

            JpaProduct jpaProduct = new JpaProduct(orderItem.getProductId());

            jpaOrderItem.setProduct(jpaProduct);
            jpaOrderItem.setOrder(new JpaOrder(orderId));
            return jpaOrderItem;
        }).collect(Collectors.toSet());

        this.repo.saveAllAndFlush(jpaOrderItems);
    }
}
