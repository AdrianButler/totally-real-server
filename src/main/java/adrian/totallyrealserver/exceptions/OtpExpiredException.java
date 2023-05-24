package adrian.totallyrealserver.exceptions;

public class OtpExpiredException extends RuntimeException
{
	public OtpExpiredException()
	{
		super("One time password has expired");
	}
}
