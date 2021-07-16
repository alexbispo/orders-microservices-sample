package br.com.alexbispo.orders.creation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "users")
public class JpaUser {

    @Id
    @GeneratedValue
    private UUID id;
}
