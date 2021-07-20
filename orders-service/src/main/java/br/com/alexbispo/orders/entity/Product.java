package br.com.alexbispo.orders.entity;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public final class Product {

    private final UUID id;

    private final BigDecimal price;

    private final long availableQuantity;

    private Product(UUID id, Optional<BigDecimal> price, long availableQuantity){
        this.id = id;
        this.price = price.orElseThrow();
        this.availableQuantity = availableQuantity;
    }

    public Product(BigDecimal price, long availableQuantity) {
        this(null, Optional.ofNullable(price), availableQuantity);
    }

    public UUID getId() {
        return id;
    }

    public long getAvailableQuantity() {
        return availableQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product addAvailableQuantity(long quantity) {
        return new Product(
                this.id,
                Optional.ofNullable(this.price),
                this.availableQuantity + quantity);
    }

    public Product setId(UUID id) {
        return new Product(id, Optional.ofNullable(this.price), this.availableQuantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", availableQuantity=" + availableQuantity +
                '}';
    }
}
