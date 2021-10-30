package constants;

public class QueryConstants {
	public static final String FIND_USER_BY_EMAIL = "SELECT u FROM User u where u.email = :email";
	public static final String GET_ALL_USERS = "SELECT u from User u";
}
