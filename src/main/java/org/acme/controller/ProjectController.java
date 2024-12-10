package org.acme.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Project;
import org.acme.repository.ProjectRepositoryImpl;

import java.util.List;

@Path("/project")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectController {

    @Inject
    ProjectRepositoryImpl projectRepository;

    @GET
    @RolesAllowed("User")
    public Response getAllProjects() {
        List<Project> projects = projectRepository.listAll();
        return Response.ok(projects).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("User")
    public Response getProjectById(@PathParam("id") Long id) {
        Project project = projectRepository.findById(id);
        if (project == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
        }
        return Response.ok(project).build();
    }

    @POST
    @RolesAllowed("User")
    @Transactional
    public Response createProject(Project project) {
        projectRepository.persist(project);
        return Response.status(Response.Status.CREATED).entity(project).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("User")
    @Transactional
    public Response updateProject(@PathParam("id") Long id, Project updatedProject) {
        Project existingProject = projectRepository.findById(id);
        if (existingProject == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
        }

        existingProject.name = updatedProject.name;
        existingProject.description = updatedProject.description;
        existingProject.owner_id = updatedProject.owner_id;
        existingProject.code = updatedProject.code;
        existingProject.picture = updatedProject.picture;
        existingProject.updatedAt = updatedProject.updatedAt;

        return Response.ok(existingProject).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("User")
    @Transactional
    public Response deleteProject(@PathParam("id") Long id) {
        Project project = projectRepository.findById(id);
        if (project == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Project not found").build();
        }
        projectRepository.delete(project);
        return Response.noContent().build();
    }
}

