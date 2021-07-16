package br.com.alexbispo.orders.creation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaOrderCreationItemsRepository extends JpaRepository<JpaOrderItem, UUID> {
}
