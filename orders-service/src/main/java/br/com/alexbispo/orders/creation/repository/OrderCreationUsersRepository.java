package br.com.alexbispo.orders.creation.repository;

import br.com.alexbispo.orders.entity.User;

import java.util.UUID;

public interface OrderCreationUsersRepository {
    boolean existsById(UUID id);

    User save(User user);
}
