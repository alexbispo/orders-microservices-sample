package br.com.alexbispo.orders.entities;

import java.util.Optional;
import java.util.UUID;

public final class User {

    private final UUID id;

    private User(Optional<UUID> id) {
        this.id = id.orElseThrow();
    }

    public User() {
        this.id = null;
    }

    public UUID getId() {
        return id;
    }

    public User setId(UUID id) {
        return new User(Optional.ofNullable(id));
    }
}
