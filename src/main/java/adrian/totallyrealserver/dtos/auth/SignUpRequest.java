package adrian.totallyrealserver.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public class SignUpRequest
{
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Name is mandatory")
	private String name;

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
