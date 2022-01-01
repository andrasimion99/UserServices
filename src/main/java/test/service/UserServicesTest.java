package test.service;

import dao.UserDao;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.UserServices;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServicesTest {
    private UserServices userServices;
    private User user;
    @Mock
    private UserDao userDao;

    @Before
    public void setup() {
        userServices = new UserServices(userDao);
        user = new User(1, "TestUser", "test1234", "test@gmail.com");
    }

    @Test
    public void givenValidId_whenGetById_thenReturnsUser() {
        when(userDao.get(user.getId())).thenReturn(user);

        User response = userServices.getById(user.getId());

        assertEquals(user, response);
    }

    @Test
    public void given_whenGetAllUsers_thenReturnAllUsers() {
        List<User> returnedList = new ArrayList<User>() {{
            add(user);
        }};
        when(userDao.getAllUsers()).thenReturn(returnedList);

        List<User> response = userServices.getAllUsers();

        assertEquals(returnedList, response);
    }

    @Test
    public void givenValidId_whenDeleteUser_thenUserIsDeleted() {
        doNothing().when(userDao).delete(user.getId());

        userServices.deleteUser(user.getId());

        verify(userDao, times(1)).delete(user.getId());
    }
}
