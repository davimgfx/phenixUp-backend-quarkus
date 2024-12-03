package org.acme.repository;

import org.acme.entity.Client;

public interface ClientRepository {
    Client findByEmail(String email);
}
