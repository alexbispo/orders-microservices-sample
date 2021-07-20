package br.com.alexbispo.orders.creation.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
public class JpaProduct {

    @Id
    @GeneratedValue
    private UUID id;

    private BigDecimal price;

    private Long availableQuantity;

    public JpaProduct() {}

    public JpaProduct(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAvailableQuantity(Long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
