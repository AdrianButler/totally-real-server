package adrian.totallyrealserver.exceptions;

public class OtpInvalidException extends RuntimeException
{
	public OtpInvalidException()
	{
		super("One time password was incorrect");
	}
}
