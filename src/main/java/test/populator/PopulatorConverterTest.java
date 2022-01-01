package test.populator;

import dto.UserDto;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import populator.Populator;
import populator.PopulatorConverter;
import populator.UserPopulator;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PopulatorConverterTest {
    private PopulatorConverter<User, UserDto> populatorConverter;
    private UserDto userDto;
    private User user;
    Populator<User, UserDto> populator;

    @Before
    public void setup() {
        populator = new UserPopulator();
        populatorConverter = new PopulatorConverter<>(populator, UserDto.class);
        userDto = new UserDto();
        user = new User(1, "TestUser", "test1234", "test@gmail.com");
    }

    @Test
    public void givenSource_whenConvert_thenReturnPopulatedTarget() {
        userDto = populatorConverter.convert(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getName(), userDto.getName());
    }

    @Test
    public void givenListOfSources_whenConvertAll_thenReturnListOfPopulatedTargets() {
        List<User> users = new ArrayList<User>() {{
            add(user);
        }};
        List<UserDto> userDtos = populatorConverter.convertAll(users);

        User firstUser = users.get(0);
        UserDto firstUserDto = userDtos.get(0);
        assertEquals(firstUser.getId(), firstUserDto.getId());
        assertEquals(firstUser.getEmail(), firstUserDto.getEmail());
        assertEquals(firstUser.getName(), firstUserDto.getName());
    }

}
