package br.com.alexbispo.orders.creation;

import java.util.UUID;

public interface UsersRepository {
	public boolean existsById(UUID id);
}
