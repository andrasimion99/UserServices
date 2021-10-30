package dto;

public class UserDto {
	private Integer id;
	private String name;
	private String email;

	public String getName() {
		return name;
	}

	public UserDto(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public UserDto() {
		super();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
