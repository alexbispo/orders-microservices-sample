package br.com.alexbispo.orders.entities;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public final class Product {

    private final UUID id;

    private final BigDecimal price;

    private final long availableQuantity;

    public Product(Optional<UUID> id, Optional<BigDecimal> price, long availableQuantity) {
        this.id = id.orElseThrow(() -> new RuntimeException("Product id is mandatory."));
        this.price = price.orElseThrow(() -> new RuntimeException("Product price is mandatory."));
        this.availableQuantity = availableQuantity;
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
                Optional.of(this.id),
                Optional.of(this.price),
                this.availableQuantity + quantity);
    }
}
