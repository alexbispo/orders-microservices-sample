package br.com.alexbispo.orders.creation;

import br.com.alexbispo.orders.entities.User;

import java.util.UUID;

public interface OrderCreationUsersRepository {
    boolean existsById(UUID id);

    User save(User user);
}
