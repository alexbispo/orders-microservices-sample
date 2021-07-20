package br.com.alexbispo.orders.creation;

import br.com.alexbispo.orders.entities.OrderItem;
import br.com.alexbispo.orders.entities.Product;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JpaOrderCreationProducts implements OrderCreationProductsRepository{

    private final JpaOrderCreationProductsRepository repo;

    public JpaOrderCreationProducts(JpaOrderCreationProductsRepository repo) {
        this.repo = repo;
    }

    @Override
    public Set<Product> findByIds(Set<UUID> ids) {
        List<JpaProduct> allById = this.repo.findAllById(ids);
        Set<Product> products = allById.stream().map(jpaProduct -> new Product(
                jpaProduct.getPrice(), jpaProduct.getAvailableQuantity()
        ).setId(jpaProduct.getId()))
                .collect(Collectors.toSet());

        return products;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        Optional<JpaProduct> foundProduct = this.repo.findById(id);
        return foundProduct.flatMap(it ->
                Optional.ofNullable(new Product(
                        it.getPrice(),
                        it.getAvailableQuantity()).setId(it.getId())));
    }

    @Override
    public Product save(Product product) {
        JpaProduct jpaProduct = new JpaProduct();
        jpaProduct.setPrice(product.getPrice());
        jpaProduct.setAvailableQuantity(product.getAvailableQuantity());
        JpaProduct savedProduct = this.repo.saveAndFlush(jpaProduct);
        return product.setId(savedProduct.getId());
    }

    @Override
    public void saveAll(Set<Product> products) {
        Set<JpaProduct> jpaProducts = products.stream().map(it -> {
            JpaProduct jpaProduct = new JpaProduct(it.getId());
            jpaProduct.setAvailableQuantity(it.getAvailableQuantity());
            jpaProduct.setPrice(it.getPrice());
            return jpaProduct;
        }).collect(Collectors.toSet());

        this.repo.saveAllAndFlush(jpaProducts);
    }
}
