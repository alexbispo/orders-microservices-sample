package br.com.alexbispo.orders.creation.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaOrderCreationUsersRepository extends JpaRepository<JpaUser, UUID> {
}
