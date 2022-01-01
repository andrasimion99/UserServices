package test.facade;

import dto.UserDto;
import entity.User;
import facade.UserFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import populator.PopulatorConverter;
import service.AuthenticationServices;
import service.EditProfileServices;
import service.UserServices;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserFacadeTest {
    UserFacade userFacade;
    private UserDto userDto;
    private User user;
    @Mock
    private UserServices userServices;
    @Mock
    private AuthenticationServices authenticationServices;
    @Mock
    private EditProfileServices editProfileServices;
    @Mock
    private PopulatorConverter<User, UserDto> populatorConverter;

    @Before
    public void setup() {
        userFacade = new UserFacade(userServices, authenticationServices, editProfileServices, populatorConverter);
        userDto = new UserDto(1, "TestUser", "test@gmail.com");
        user = new User(1, "TestUser", "test1234", "test@gmail.com");
    }

    @Test
    public void givenEmailPasswordAndName_whenCreateUser_thenReturnCreatedUserDto() {
        when(authenticationServices.createUser(user.getEmail(), user.getPassword(), user.getName())).thenReturn(user);
        when(populatorConverter.convert(user)).thenReturn(userDto);

        UserDto response = userFacade.createUser(user.getEmail(), user.getPassword(), user.getName());

        assertEquals(userDto, response);
    }

    @Test
    public void givenInvalidUserData_whenCreateUser_thenReturnsNull() {
        when(authenticationServices.createUser(isA(String.class), isA(String.class), isA(String.class))).thenReturn(null);

        UserDto response = userFacade.createUser(user.getEmail(), user.getPassword(), user.getName());

        assertNull(response);
    }

    @Test
    public void givenEmailPassword_whenLogin_thenReturnLoggedInUserDto() {
        when(authenticationServices.login(user.getEmail(), user.getPassword())).thenReturn(user);
        when(populatorConverter.convert(user)).thenReturn(userDto);

        UserDto response = userFacade.login(user.getEmail(), user.getPassword());

        assertEquals(userDto, response);
    }

    @Test
    public void givenInvalidUserData_whenLogin_thenReturnNull() {
        when(authenticationServices.login(isA(String.class), isA(String.class))).thenReturn(null);

        UserDto response = userFacade.login(user.getEmail(), user.getPassword());

        assertNull(response);
    }

    @Test
    public void givenId_whenGetById_thenReturnUserDto() {
        when(userServices.getById(user.getId())).thenReturn(user);
        when(populatorConverter.convert(user)).thenReturn(userDto);

        UserDto response = userFacade.getById(user.getId());

        assertEquals(userDto, response);
    }

    @Test
    public void givenInvalidId_whenGetById_thenThrowsException() {
        doThrow(new EntityNotFoundException()).when(userServices).getById(user.getId());

        EntityNotFoundException exception = null;
        try {
            userFacade.getById(user.getId());
        } catch (EntityNotFoundException ex) {
            exception = ex;
        }

        assertNotNull(exception);
    }

    @Test
    public void given_whenGetAllUsers_thenReturnListOfUsersDto() {
        List<User> users = new ArrayList<User>() {{
            add(user);
        }};
        List<UserDto> userDtos = new ArrayList<UserDto>() {{
            add(userDto);
        }};
        when(userServices.getAllUsers()).thenReturn(users);
        when(populatorConverter.convertAll(users)).thenReturn(userDtos);

        List<UserDto> response = userFacade.getAllUsers();

        assertEquals(userDtos, response);
    }

    @Test
    public void givenId_whenDeleteUser_thenUserIsDeleted() {
        doNothing().when(userServices).deleteUser(user.getId());

        userFacade.deleteUser(user.getId());

        verify(userServices, times(1)).deleteUser(user.getId());
    }

    @Test
    public void givenInvalidId_whenDeleteUser_thenThrowsException() {
        doThrow(new EntityNotFoundException()).when(userServices).deleteUser(user.getId());

        EntityNotFoundException exception = null;
        try {
            userFacade.deleteUser(user.getId());
        } catch (EntityNotFoundException ex) {
            exception = ex;
        }

        assertNotNull(exception);
    }

    @Test
    public void givenNewEmail_whenEditProfile_thenReturnUpdatedUserDto() {
        User updatedUser = user;
        updatedUser.setEmail("NewEmail");
        userDto.setEmail(updatedUser.getEmail());
        when(userServices.getById(user.getId())).thenReturn(user);
        when(editProfileServices.editEmail(user.getId(), updatedUser.getEmail())).thenReturn(updatedUser);
        when(populatorConverter.convert(updatedUser)).thenReturn(userDto);

        UserDto response = userFacade.editProfile(user.getId(), null, updatedUser.getEmail(), null);

        assertEquals(userDto, response);
    }

    @Test
    public void givenNewName_whenEditProfile_thenReturnUpdatedUserDto() {
        User updatedUser = user;
        updatedUser.setName("NewName");
        userDto.setName(updatedUser.getName());
        when(userServices.getById(user.getId())).thenReturn(user);
        when(editProfileServices.editName(user.getId(), updatedUser.getName())).thenReturn(updatedUser);
        when(populatorConverter.convert(updatedUser)).thenReturn(userDto);

        UserDto response = userFacade.editProfile(user.getId(), user.getName(), null, null);

        assertEquals(userDto, response);
    }

    @Test
    public void givenNewPassword_whenEditProfile_thenReturnUpdatedUserDto() {
        User updatedUser = user;
        updatedUser.setPassword("NewPassword");
        when(userServices.getById(user.getId())).thenReturn(user);
        when(editProfileServices.editPassword(user.getId(), updatedUser.getPassword())).thenReturn(updatedUser);
        when(populatorConverter.convert(updatedUser)).thenReturn(userDto);

        UserDto response = userFacade.editProfile(user.getId(), null, null, updatedUser.getPassword());

        assertEquals(userDto, response);
    }

    @Test
    public void givenOnlyId_whenEditProfile_thenReturnSameUserDto() {
        when(userServices.getById(user.getId())).thenReturn(user);
        when(populatorConverter.convert(user)).thenReturn(userDto);

        UserDto response = userFacade.editProfile(user.getId(), null, null, null);

        assertEquals(userDto, response);
    }

    @Test
    public void givenInvalidId_whenEditProfile_thenThrowsException() {
        doThrow(new EntityNotFoundException()).when(userServices).getById(user.getId());

        EntityNotFoundException exception = null;
        try {
            userFacade.editProfile(user.getId(), null, null, null);
        } catch (EntityNotFoundException ex) {
            exception = ex;
        }

        assertNotNull(exception);
    }

    @Test
    public void givenNewEmailAndName_whenEditProfile_thenReturnUpdatedUserDto() {
        User updatedUser = user;
        updatedUser.setEmail("NewEmail");
        updatedUser.setName("NewName");
        userDto.setEmail(updatedUser.getEmail());
        userDto.setName(updatedUser.getName());
        when(userServices.getById(user.getId())).thenReturn(user);
        when(editProfileServices.editEmail(user.getId(), updatedUser.getEmail())).thenReturn(updatedUser);
        when(editProfileServices.editName(user.getId(), updatedUser.getName())).thenReturn(updatedUser);
        when(populatorConverter.convert(updatedUser)).thenReturn(userDto);

        UserDto response = userFacade.editProfile(user.getId(), updatedUser.getName(), updatedUser.getEmail(), null);

        assertEquals(userDto, response);
    }

}
