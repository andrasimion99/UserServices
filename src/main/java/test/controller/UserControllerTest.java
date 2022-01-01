package test.controller;

import controller.UserController;
import dao.JpaDao;
import dto.LoginUser;
import dto.RegisterUser;
import dto.UserDto;
import entity.User;
import facade.UserFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    UserController userController;
    private UserDto userDto;
    private User user;
    @Mock
    UserFacade userFacade;

    @Before
    public void setup() {
        userController = new UserController(userFacade);
        userDto = new UserDto(1, "TestUser", "test@gmail.com");
        user = new User(1, "TestUser", "test1234", "test@gmail.com");
    }

    @Test
    public void given_whenGetAllUsers_thenReturnOkWithUsers() {
        List<UserDto> users = new ArrayList<UserDto>() {{
            add(userDto);
        }};
        when(userFacade.getAllUsers()).thenReturn(users);

        Response response = userController.GetAllUsers();

        assertEquals(200, response.getStatus());
        assertEquals(users, response.getEntity());
    }

    @Test
    public void givenId_whenGetUserById_thenReturnOkWithUser() {
        when(userFacade.getById(user.getId())).thenReturn(userDto);

        Response response = userController.GetUserById(user.getId());

        assertEquals(200, response.getStatus());
        assertEquals(userDto, response.getEntity());
    }

    @Test
    public void givenInexistentId_whenGetUserById_thenReturnNotFound() {
        doThrow(new EntityNotFoundException()).when(userFacade).getById(user.getId());

        Response response = userController.GetUserById(user.getId());

        assertEquals(404, response.getStatus());
    }

    @Test
    public void givenLoginUser_whenLoginUser_thenReturnOkWithUser() {
        LoginUser loginUser = new LoginUser(user.getEmail(), user.getPassword());
        when(userFacade.login(loginUser.getEmail(), loginUser.getPassword())).thenReturn(userDto);

        Response response = userController.LoginUser(loginUser);

        assertEquals(200, response.getStatus());
        assertEquals(userDto, response.getEntity());
    }

    @Test
    public void givenWrongCredentials_whenLoginUser_thenReturnBadRequest() {
        LoginUser loginUser = new LoginUser(user.getEmail(), user.getPassword());
        when(userFacade.login(loginUser.getEmail(), loginUser.getPassword())).thenReturn(null);

        Response response = userController.LoginUser(loginUser);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void givenRegisterUser_whenRegisterUser_thenReturnCreated() {
        RegisterUser registerUser = new RegisterUser(user.getName(), user.getEmail(), user.getPassword());
        when(userFacade.createUser(registerUser.getEmail(), registerUser.getPassword(), registerUser.getName())).thenReturn(userDto);

        Response response = userController.RegisterUser(registerUser);

        assertEquals(201, response.getStatus());
    }

    @Test
    public void givenExistentUser_whenRegisterUser_thenReturnBadRequest() {
        RegisterUser registerUser = new RegisterUser(user.getName(), user.getEmail(), user.getPassword());
        when(userFacade.createUser(registerUser.getEmail(), registerUser.getPassword(), registerUser.getName())).thenReturn(null);

        Response response = userController.RegisterUser(registerUser);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void givenId_whenDeleteUser_thenReturnNoContent() {
        doNothing().when(userFacade).deleteUser(user.getId());

        Response response = userController.DeleteUser(user.getId());

        assertEquals(204, response.getStatus());
    }

    @Test
    public void givenInvalidId_whenDeleteUser_thenReturnNotFound() {
        doThrow(new EntityNotFoundException()).when(userFacade).deleteUser(user.getId());

        Response response = userController.DeleteUser(user.getId());

        assertEquals(404, response.getStatus());
    }

    @Test
    public void givenIdAndRegisterUser_whenEditUser_thenReturnNoContentWithUser() {
        RegisterUser registerUser = new RegisterUser(user.getName(), user.getEmail(), user.getPassword());
        when(userFacade.editProfile(user.getId(), registerUser.getName(), registerUser.getEmail(), registerUser.getPassword())).thenReturn(userDto);

        Response response = userController.EditUser(user.getId(), registerUser);

        assertEquals(204, response.getStatus());
        assertEquals(userDto, response.getEntity());
    }

    @Test
    public void givenInvalidIdAndRegisterUser_whenEditUser_thenReturnNotFound() {
        RegisterUser registerUser = new RegisterUser(user.getName(), user.getEmail(), user.getPassword());
        doThrow(new EntityNotFoundException()).when(userFacade).editProfile(user.getId(), registerUser.getName(), registerUser.getEmail(), registerUser.getPassword());

        Response response = userController.EditUser(user.getId(), registerUser);

        assertEquals(404, response.getStatus());
    }
}
