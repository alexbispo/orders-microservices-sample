package br.com.alexbispo.orders.creation.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaOrderCreationOrdersRepository extends JpaRepository<JpaOrder, UUID> {

    @EntityGraph(attributePaths = "orderItems.product")
    Optional<JpaOrder> findById(UUID id);
}
