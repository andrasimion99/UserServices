package test.service;

import dao.UserDao;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.EditProfileServices;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EditProfileServicesTest {
    private EditProfileServices editProfileServices;
    private User user;
    @Mock
    private UserDao userDao;

    @Before
    public void setup() {
        editProfileServices = new EditProfileServices(userDao);
        user = new User(1, "TestUser", "test1234", "test@gmail.com");
    }

    @Test
    public void givenValidIdAndEmail_whenEditEmail_thenReturnsUpdatedUser() {
        User updatedUser = user;
        updatedUser.setEmail("NewEmail");
        when(userDao.get(user.getId())).thenReturn(user);
        when(userDao.update(user)).thenReturn(updatedUser);

        User response = editProfileServices.editEmail(user.getId(), updatedUser.getEmail());

        assertEquals(updatedUser, response);
    }

    @Test
    public void givenInvalidId_whenEditEmail_thenThrowsException() {
        doThrow(new EntityNotFoundException()).when(userDao).get(user.getId());

        EntityNotFoundException exception = null;
        try {
            editProfileServices.editEmail(user.getId(), user.getEmail());
        } catch (EntityNotFoundException ex) {
            exception = ex;
        }

        assertNotNull(exception);
    }

    @Test
    public void givenValidIdAndName_whenEditName_thenReturnsUpdatedUser() {
        User updatedUser = user;
        updatedUser.setName("NewName");
        when(userDao.get(user.getId())).thenReturn(user);
        when(userDao.update(user)).thenReturn(updatedUser);

        User response = editProfileServices.editEmail(user.getId(), updatedUser.getName());

        assertEquals(updatedUser, response);
    }

    @Test
    public void givenInvalidId_whenEditName_thenThrowsException() {
        doThrow(new EntityNotFoundException()).when(userDao).get(user.getId());

        EntityNotFoundException exception = null;
        try {
            editProfileServices.editName(user.getId(), user.getName());
        } catch (EntityNotFoundException ex) {
            exception = ex;
        }

        assertNotNull(exception);
    }

    @Test
    public void givenValidIdAndPassword_whenEditPassword_thenReturnsUpdatedUser() {
        User updatedUser = user;
        updatedUser.setPassword("newPassword");
        when(userDao.get(user.getId())).thenReturn(user);
        when(userDao.update(user)).thenReturn(updatedUser);

        User response = editProfileServices.editEmail(user.getId(), updatedUser.getPassword());

        assertEquals(updatedUser, response);
    }

    @Test
    public void givenInvalidId_whenEditPassword_thenThrowsException() {
        doThrow(new EntityNotFoundException()).when(userDao).get(user.getId());

        EntityNotFoundException exception = null;
        try {
            editProfileServices.editPassword(user.getId(), user.getPassword());
        } catch (EntityNotFoundException ex) {
            exception = ex;
        }

        assertNotNull(exception);
    }
}
