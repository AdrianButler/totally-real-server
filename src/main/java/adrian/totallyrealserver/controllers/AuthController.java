package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.dtos.auth.LoginRequest;
import adrian.totallyrealserver.dtos.auth.SignUpRequest;
import adrian.totallyrealserver.dtos.auth.VerifyRequest;
import adrian.totallyrealserver.exceptions.OtpExpiredException;
import adrian.totallyrealserver.exceptions.OtpInvalidException;
import adrian.totallyrealserver.exceptions.UserAlreadyExistsException;
import adrian.totallyrealserver.exceptions.UserDoesNotExistException;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import adrian.totallyrealserver.services.AuthService;
import adrian.totallyrealserver.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController
{
	private final StoreUserRepository storeUserRepository;

	private final AuthService authService;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	public AuthController(StoreUserRepository storeUserRepository, AuthService authService, PasswordEncoder passwordEncoder, JwtService jwtService)
	{
		this.storeUserRepository = storeUserRepository;
		this.authService = authService;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@RequestBody SignUpRequest signUpRequest)
	{
		try
		{
			authService.createUser(signUpRequest);
		} catch (UserAlreadyExistsException exception)
		{
			throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
		}
	}

	@PostMapping("/signup/verify")
	@ResponseStatus(HttpStatus.CREATED)
	public String verifySignUp(@RequestBody VerifyRequest verifyRequest) //TODO refactor this to make sure accounts are verified before letting them login
	{
		try
		{
			return authService.verifyOTP(verifyRequest);
		} catch (OtpExpiredException | OtpInvalidException exception)
		{
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage());
		}
	}

	@PostMapping("/login")
	public void loginUser(@RequestBody LoginRequest loginRequest)
	{
		// Send OTP email to user
		try
		{
			authService.loginUser(loginRequest);
		}
		catch (UserDoesNotExistException exception)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
		}

	}

	@PostMapping("/login/verify")
	public String verifyLogin(@RequestBody VerifyRequest verifyRequest)
	{
		try
		{
			return authService.verifyOTP(verifyRequest);
		} catch (OtpExpiredException | OtpInvalidException exception)
		{
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage());
		}

	}

}
