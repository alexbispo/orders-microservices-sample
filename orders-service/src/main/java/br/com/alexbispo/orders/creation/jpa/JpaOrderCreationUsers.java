package br.com.alexbispo.orders.creation.jpa;

import br.com.alexbispo.orders.creation.OrderCreationUsersRepository;
import br.com.alexbispo.orders.entities.User;
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

    @Override
    public User save(User user) {
        JpaUser jpaUser = new JpaUser();
        jpaUser.setId(user.getId());
        JpaUser savedUser = this.repo.save(jpaUser);
        this.repo.flush();
        return user.setId(savedUser.getId());
    }
}
