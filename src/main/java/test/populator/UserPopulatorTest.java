package test.populator;

import dto.UserDto;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import populator.UserPopulator;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserPopulatorTest {
    UserPopulator userPopulator;
    private UserDto userDto;
    private User user;

    @Before
    public void setup() {
        userPopulator = new UserPopulator();
        userDto = new UserDto();
        user = new User(1, "TestUser", "test1234", "test@gmail.com");
    }

    @Test
    public void givenUser_whenPopulate_thenUserDtoIsPopulated(){
        userPopulator.populate(user, userDto);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getName(), userDto.getName());
    }
}
