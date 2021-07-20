package br.com.alexbispo.orders.creation.repository;

import br.com.alexbispo.orders.entity.Product;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface OrderCreationProductsRepository {
    Set<Product> findByIds(Set<UUID> productIds);

    Optional<Product> findById(UUID id);

    Product save(Product product);

    void saveAll(Set<Product> products);
}
