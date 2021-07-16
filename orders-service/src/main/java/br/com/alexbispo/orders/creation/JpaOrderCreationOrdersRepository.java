package br.com.alexbispo.orders.creation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaOrderCreationOrdersRepository extends JpaRepository<JpaOrder, UUID> {
}
