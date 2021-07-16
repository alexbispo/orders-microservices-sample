package br.com.alexbispo.orders.creation;

import br.com.alexbispo.orders.entities.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public class JpaOrderCreationItems implements OrderCreationItemsRepository{

    private final JpaOrderCreationItemsRepository repo;

    public JpaOrderCreationItems(JpaOrderCreationItemsRepository repo) {
        this.repo = repo;
    }

    @Override
    public Set<OrderItem> findByIds(Set<UUID> ids) {
        return null;
    }

    @Override
    public void save(OrderItem orderItem) {

    }
}
