package populator;

import dto.UserDto;
import entity.User;

public class UserPopulator implements Populator<User, UserDto> {

	@Override
	public void populate(User user, UserDto userDto) {
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getId());
	}
}
