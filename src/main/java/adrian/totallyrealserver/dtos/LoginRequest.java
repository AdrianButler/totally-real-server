package adrian.totallyrealserver.dtos;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest
{
	@NotBlank(message = "Email is mandatory")
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
