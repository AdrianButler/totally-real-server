package adrian.totallyrealserver.exceptions;

public class UserAlreadyExistsException extends Exception
{
	public UserAlreadyExistsException()
	{
		super("User already exists in the database");
	}
}
