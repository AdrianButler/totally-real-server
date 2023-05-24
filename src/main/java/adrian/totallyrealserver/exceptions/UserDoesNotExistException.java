package adrian.totallyrealserver.exceptions;

public class UserDoesNotExistException extends RuntimeException
{
	public UserDoesNotExistException()
	{
		super("User does not exists");
	}
}
