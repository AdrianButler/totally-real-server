package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.dtos.auth.VerifyRequest;
import adrian.totallyrealserver.dtos.auth.SignUpRequest;
import adrian.totallyrealserver.models.StoreUser;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import adrian.totallyrealserver.services.EmailAuthService;
import adrian.totallyrealserver.services.JwtService;
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
	private final StoreUserRepository storeUserRepository;

	private final EmailAuthService emailAuthService;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	public AuthController(StoreUserRepository storeUserRepository, EmailAuthService emailAuthService, PasswordEncoder passwordEncoder, JwtService jwtService)
	{
		this.storeUserRepository = storeUserRepository;
		this.emailAuthService = emailAuthService;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
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

		emailAuthService.sendOneTimePassword(newUser);
	}

	@PostMapping("/signup/verify")
	@ResponseStatus(HttpStatus.CREATED)
	public String verifySignUp(@RequestBody VerifyRequest verifyRequest)
	{
		StoreUser storeUser = verifyOTP(verifyRequest);

		return jwtService.generateToken(storeUser);
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
	public String verifyLogin(@RequestBody VerifyRequest verifyRequest)
	{
		StoreUser storeUser = verifyOTP(verifyRequest);

		return jwtService.generateToken(storeUser);
	}

	private StoreUser verifyOTP(@RequestBody VerifyRequest verifyRequest) // TODO refactor this to email service
	{
		String email = verifyRequest.getEmail();
		String oneTimePassword = verifyRequest.getOneTimePassword();

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
