package br.com.alexbispo.orders.creation;

import java.util.UUID;

public interface OrderCreationUsersRepository {
	public boolean existsById(UUID id);
}
