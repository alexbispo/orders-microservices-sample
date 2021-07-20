package br.com.alexbispo.orders.creation.jpa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "orders")
public class JpaOrder {

    @Id
    @GeneratedValue
    private UUID id;

    private BigDecimal amount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<JpaOrderItem> orderItems = new ArrayList<>();

    public JpaOrder() {}

    public JpaOrder(UUID orderId) {
        this.id = orderId;
    }

    public UUID getId() {
        return id;
    }

    public List<JpaOrderItem> getItems() {
        return Collections.unmodifiableList(orderItems);
    }

    public void addItem(JpaOrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }

    public void removeItem(JpaOrderItem item) {
        orderItems.remove(item);
        item.setOrder(null);
    }

    public void addItems(Set<JpaOrderItem> orderItems) {
        orderItems.forEach(item -> addItem(item));
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
