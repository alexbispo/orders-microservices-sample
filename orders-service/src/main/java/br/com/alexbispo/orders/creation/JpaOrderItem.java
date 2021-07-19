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
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "ORDER_ID_FK"))
    private JpaOrder order;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "PRODUCT_ID_FK"))
    private JpaProduct product;

    private BigDecimal amount;

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

    public UUID getProductId() {
        return product.getId();
    }

    public BigDecimal getProductPrice() {
        return product.getPrice();
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getProductAvailableQuantity() {
        return product.getAvailableQuantity();
    }
}
