package br.com.alexbispo.orders.creation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class JpaOrder {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaOrderItem> orderItems = new ArrayList<>();

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
}
