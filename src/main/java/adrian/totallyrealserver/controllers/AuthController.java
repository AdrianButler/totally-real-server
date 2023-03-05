package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.dtos.auth.LoginRequest;
import adrian.totallyrealserver.dtos.auth.SignUpRequest;
import adrian.totallyrealserver.models.StoreUser;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import adrian.totallyrealserver.services.EmailAuthService;
import jakarta.validation.constraints.Email;
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
	final StoreUserRepository storeUserRepository;

	final EmailAuthService emailAuthService;

	final PasswordEncoder passwordEncoder;

	public AuthController(StoreUserRepository storeUserRepository, EmailAuthService emailAuthService, PasswordEncoder passwordEncoder)
	{
		this.storeUserRepository = storeUserRepository;
		this.emailAuthService = emailAuthService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@RequestBody SignUpRequest signUpRequest)
	{
		if (storeUserRepository.existsByEmail(signUpRequest.getEmail())) // check if user with email already exists
		{
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
		}

		StoreUser newUser = new StoreUser(signUpRequest.getName(), signUpRequest.getEmail());
		storeUserRepository.save(newUser);
	}

	@PostMapping("/login")
	public void loginUser(@Email @RequestBody String email)
	{
		// Send OTP email to user

		StoreUser storeUser = storeUserRepository.findStoreUserByEmail(email);

		if (storeUser == null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
		}

		emailAuthService.sendOneTimePassword(storeUser);
	}

	@PostMapping("/login/verify")
	public StoreUser verifyLogin(@RequestBody LoginRequest loginRequest)
	{
		String email = loginRequest.getEmail();
		String oneTimePassword = loginRequest.getOneTimePassword();

		StoreUser storeUser = storeUserRepository.findStoreUserByEmail(email);

		if (emailAuthService.checkIfOtpExpired(storeUser.getOtpRequestDate()))
		{
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "One time password has expired");
		}

		if (!passwordEncoder.matches(oneTimePassword, storeUser.getOneTimePassword()))
		{
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "One time password was incorrect");
		}

		return storeUser;
	}

}
