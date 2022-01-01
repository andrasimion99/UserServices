package test.dao;

import constants.QueryConstants;
import dao.JpaDao;
import dao.UserDao;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
    UserDao userDao;
    User user;
    @Mock
    JpaDao<User> jpaDao;

    @Before
    public void setup() {
        userDao = new UserDao(jpaDao);
        user = new User(1, "TestUser", "test1234", "test@gmail.com");
    }

    @Test
    public void givenValidId_whenGet_thenReturnsUser() {
        when(jpaDao.get(User.class, user.getId())).thenReturn(user);

        User response = userDao.get(user.getId());

        assertEquals(user, response);
    }

    @Test
    public void givenInvalidId_whenGet_thenThrowException() {
        when(jpaDao.get(User.class, user.getId())).thenReturn(null);
        EntityNotFoundException exception = null;

        try {
            userDao.get(user.getId());
        } catch (EntityNotFoundException ex) {
            exception = ex;
        }

        assertNotNull(exception);
    }

    @Test
    public void givenNewUser_whenCreate_ThenReturnSavedUser() {
        when(jpaDao.create(user)).thenReturn(user);

        User response = userDao.create(user);

        assertEquals(user, response);
    }

    @Test
    public void givenUser_whenUpdate_ThenReturnUpdatedUser() {
        User updatedUser = user;
        updatedUser.setName("NewName");
        when(jpaDao.update(user)).thenReturn(updatedUser);

        User response = userDao.update(user);

        assertEquals(updatedUser, response);
    }

    @Test
    public void givenValidId_whenDelete_ThenUserIsDeleted() {
        doNothing().when(jpaDao).delete(User.class, user.getId());

        userDao.delete(user.getId());

        verify(jpaDao, times(1)).delete(User.class, user.getId());
    }

    @Test
    public void given_whenGetAllUsers_ThenReturnListOfUsers() {
        List<User> returnedList = new ArrayList<User>() {{
            add(user);
        }};
        when(jpaDao.getAll(QueryConstants.GET_ALL_USERS)).thenReturn(returnedList);

        List<User> response = userDao.getAllUsers();

        assertEquals(returnedList, response);
    }

    @Test
    public void givenValidEmail_whenFindByEmail_ThenReturnUser() {
        Optional<User> optionalUser = Optional.of(user);
        when(jpaDao.getByProperty(QueryConstants.FIND_USER_BY_EMAIL, user.getEmail())).thenReturn(optionalUser);

        Optional<User> response = userDao.findByEmail(user.getEmail());

        assertEquals(optionalUser, response);
    }
}
