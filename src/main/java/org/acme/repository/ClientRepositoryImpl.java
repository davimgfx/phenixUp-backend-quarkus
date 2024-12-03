package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Client;

@ApplicationScoped
public class ClientRepositoryImpl implements ClientRepository, PanacheRepository<Client> {

    @Override
    public Client findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
