package adrian.totallyrealserver.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public class VerifyRequest
{
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "oneTimePassword is mandatory")
	private String oneTimePassword;


	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getOneTimePassword()
	{
		return oneTimePassword;
	}

	public void setOneTimePassword(String oneTimePassword)
	{
		this.oneTimePassword = oneTimePassword;
	}
}
