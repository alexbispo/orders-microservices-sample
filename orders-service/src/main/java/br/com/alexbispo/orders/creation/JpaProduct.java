package br.com.alexbispo.orders.creation;

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

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getAvailableQuantity() {
        return availableQuantity;
    }
}
