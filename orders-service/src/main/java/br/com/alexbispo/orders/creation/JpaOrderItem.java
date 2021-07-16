package br.com.alexbispo.orders.creation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class JpaOrderItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private JpaOrder order;

    private Long availableQuantity;

    private BigDecimal amount;

    private BigDecimal price;

    private Long quantity;

    public UUID getId() {
        return id;
    }

    public JpaOrder getOrder() {
        return order;
    }

    public void setOrder(JpaOrder order) {
        this.order = order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    public Long getQuantity() {
        return quantity;
    }
}
