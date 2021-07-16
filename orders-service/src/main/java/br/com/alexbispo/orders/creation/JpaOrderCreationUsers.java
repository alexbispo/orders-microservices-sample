package br.com.alexbispo.orders.creation;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JpaOrderCreationUsers implements OrderCreationUsersRepository {

    private final JpaOrderCreationUsersRepository repo;

    public JpaOrderCreationUsers(JpaOrderCreationUsersRepository repo) {
        this.repo = repo;
    }
    @Override
    public boolean existsById(UUID id) {
        return this.repo.existsById(id);
    }
}
