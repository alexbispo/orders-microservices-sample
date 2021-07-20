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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "ORDER_ID_FK"))
    private JpaOrder order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "PRODUCT_ID_FK"))
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void setProduct(JpaProduct product) {
        this.product = product;
    }
}
