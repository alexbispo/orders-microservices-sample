package br.com.alexbispo.orders.creation;

import br.com.alexbispo.orders.entities.Product;

import java.util.Set;
import java.util.UUID;

public interface OrderCreationProductsRepository {
    Set<Product> findByIds(Set<UUID> orderItemsIds);

    void save(Product product);
}
