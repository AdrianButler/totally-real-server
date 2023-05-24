package adrian.totallyrealserver.exceptions;

public class UserAlreadyExistsException extends RuntimeException
{
	public UserAlreadyExistsException()
	{
		super("User already exists in the database");
	}
}
