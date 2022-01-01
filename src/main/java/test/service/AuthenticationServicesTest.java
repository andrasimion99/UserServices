package test.service;

import dao.UserDao;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.AuthenticationServices;
import service.EditProfileServices;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServicesTest {
    private AuthenticationServices authenticationServices;
    private User user;
    @Mock
    private UserDao userDao;

    @Before
    public void setup() {
        authenticationServices = new AuthenticationServices(userDao);
        user = new User(1, "TestUser", "test1234", "test@gmail.com");
    }

    @Test
    public void givenEmailAndPassword_whenLogin_thenReturnsUser() {
        Optional<User> optionalUser = Optional.of(user);
        when(userDao.findByEmail(user.getEmail())).thenReturn(optionalUser);

        User response = authenticationServices.login(user.getEmail(), user.getPassword());

        assertEquals(user, response);
    }

    @Test
    public void givenWrongEmailAndPassword_whenLogin_thenReturnsNull() {
        Optional<User> optionalUser = Optional.empty();
        when(userDao.findByEmail(user.getEmail())).thenReturn(optionalUser);

        User response = authenticationServices.login(user.getEmail(), user.getPassword());

        assertNull(response);
    }

    @Test
    public void givenEmailAndWrongPassword_whenLogin_thenReturnsNull() {
        Optional<User> optionalUser = Optional.of(user);
        when(userDao.findByEmail(user.getEmail())).thenReturn(optionalUser);

        User response = authenticationServices.login(user.getEmail(), "wrong");

        assertNull(response);
    }

    @Test
    public void givenExistentEmail_whenUserExistsWithEmail_thenReturnsTrue() {
        Optional<User> optionalUser = Optional.of(user);
        when(userDao.findByEmail(user.getEmail())).thenReturn(optionalUser);

        boolean response = authenticationServices.userExistsWithEmail(user.getEmail());

        assertTrue(response);
    }

    @Test
    public void givenInexistentEmail_whenUserExistsWithEmail_thenReturnsFalse() {
        Optional<User> optionalUser = Optional.empty();
        when(userDao.findByEmail(user.getEmail())).thenReturn(optionalUser);

        boolean response = authenticationServices.userExistsWithEmail(user.getEmail());

        assertFalse(response);
    }

    @Test
    public void givenEmailPasswordAndName_whenCreateUser_thenReturnsUserCreated() {
        when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userDao.create(isA(User.class))).thenReturn(user);

        User response = authenticationServices.createUser(user.getEmail(), user.getPassword(), user.getName());

        assertEquals(user, response);
    }

    @Test
    public void givenExistentEmail_whenCreateUser_thenReturnsNull() {
        Optional<User> optionalUser = Optional.of(user);
        when(userDao.findByEmail(user.getEmail())).thenReturn(optionalUser);

        User response = authenticationServices.createUser(user.getEmail(), user.getPassword(), user.getName());

        assertNull(response);
    }
}
