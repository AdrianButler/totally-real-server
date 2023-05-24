package adrian.totallyrealserver.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public class LoginRequest
{
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}
