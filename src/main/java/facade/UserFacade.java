package facade;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import dto.UserDto;
import entity.User;
import populator.PopulatorConverter;
import populator.UserPopulator;
import service.AuthenticationServices;
import service.EditProfileServices;
import service.UserServices;

public class UserFacade {
    @Inject
    private UserServices userServices;
    @Inject
    private AuthenticationServices authenticationServices;
    @Inject
    private EditProfileServices editProfileServices;
    private PopulatorConverter<User, UserDto> populatorConverter;

    public UserFacade() {
        this.populatorConverter = new PopulatorConverter<>(new UserPopulator(), UserDto.class);
    }

    public UserFacade(UserServices userServices, AuthenticationServices authenticationServices, EditProfileServices editProfileServices, PopulatorConverter<User, UserDto> populatorConverter) {
        this.userServices = userServices;
        this.authenticationServices = authenticationServices;
        this.editProfileServices = editProfileServices;
        this.populatorConverter = populatorConverter;
    }

    public UserFacade(UserServices userServices, PopulatorConverter<User, UserDto> populatorConverter) {
        this.userServices = userServices;
        this.populatorConverter = populatorConverter;
    }

    public UserDto createUser(String email, String password, String name) {
        User user = authenticationServices.createUser(email, password, name);
        if (user != null) {
            return populatorConverter.convert(user);
        }
        return null;
    }

    public UserDto login(String email, String password) {
        User user = authenticationServices.login(email, password);
        if (user != null) {
            return populatorConverter.convert(user);
        }
        return null;
    }

    public UserDto getById(Integer id) throws EntityNotFoundException {
        return populatorConverter.convert(userServices.getById(id));
    }

    public List<UserDto> getAllUsers() {
        return populatorConverter.convertAll(userServices.getAllUsers());
    }

    public void deleteUser(Integer id) throws EntityNotFoundException {
        userServices.deleteUser(id);
    }

    public UserDto editProfile(Integer id, String name, String email, String password) throws EntityNotFoundException {
        User user = userServices.getById(id);
        if (name != null) {
            user = editProfileServices.editName(id, name);
        }
        if (email != null) {
            user = editProfileServices.editEmail(id, email);
        }
        if (password != null) {
            user = editProfileServices.editPassword(id, password);
        }
        return populatorConverter.convert(user);

    }
}
