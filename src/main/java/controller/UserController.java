package controller;

import dto.LoginUser;
import dto.RegisterUser;
import dto.UserDto;
import facade.UserFacade;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/users")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserFacade facade;

    public UserController() {
    }

    public UserController(UserFacade facade){
        this.facade = facade;
    }

    @GET
    public Response GetAllUsers() {
        return Response.ok(facade.getAllUsers()).build();
    }

    @GET
    @Path("/{id}")
    public Response GetUserById(@PathParam("id") int id) {
        try {
            UserDto user = facade.getById(id);
            return Response.ok(user).build();
        } catch (EntityNotFoundException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response LoginUser(LoginUser loginUser) {
        UserDto user = facade.login(loginUser.getEmail(), loginUser.getPassword());
        if (user == null)
            return Response.status(400).entity("Login failed. Wrong credentials.").build();
        return Response.ok(user).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response RegisterUser(RegisterUser registerUser) {
        UserDto user = facade.createUser(registerUser.getEmail(), registerUser.getPassword(), registerUser.getName());
        if (user == null)
            return Response.status(400).entity("User already exists").build();
        try {
            URI uri = new URI("users/" + user.getId());
            return Response.created(uri).build();
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response EditUser(@PathParam("id") int id, RegisterUser registerUser) {
        try {
            UserDto user = facade.editProfile(id, registerUser.getName(), registerUser.getEmail(), registerUser.getPassword());
            return Response.noContent().entity(user).build();
        } catch (EntityNotFoundException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response DeleteUser(@PathParam("id") int id) {
        try {
            facade.deleteUser(id);
            return Response.noContent().build();
        } catch (EntityNotFoundException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        }
    }
}
