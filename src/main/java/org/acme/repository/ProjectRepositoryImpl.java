package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Project;

@ApplicationScoped
public class ProjectRepositoryImpl implements  PanacheRepository<Project> {
}
