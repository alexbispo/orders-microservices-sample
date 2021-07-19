package br.com.alexbispo.orders.creation;

import br.com.alexbispo.orders.entities.OrderItem;
import br.com.alexbispo.orders.entities.Product;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public class JpaOrderCreationProducts implements OrderCreationProductsRepository{

    private final JpaOrderCreationProductsRepository repo;

    public JpaOrderCreationProducts(JpaOrderCreationProductsRepository repo) {
        this.repo = repo;
    }

    @Override
    public Set<Product> findByIds(Set<UUID> ids) {
        return null;
    }

    @Override
    public void save(Product product) {

    }
}
