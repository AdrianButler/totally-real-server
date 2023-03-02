package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.models.StoreUser;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import adrian.totallyrealserver.services.EmailAuthService;
import java.util.Date;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
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
	public void createUser(@RequestBody StoreUser user)
	{
		StoreUser userFromSearch = storeUserRepository.findStoreUserByEmail(user.getEmail());

		if (userFromSearch != null) // if user already exists return that user
		{
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
		}

		StoreUser newUser = new StoreUser(user.getName(), user.getEmail());
		storeUserRepository.save(newUser);
	}

	@PostMapping("/login")
	public void loginUser(@RequestBody Map<String, String> requestBody)
	{
		// Send OTP email to user

		final String email = requestBody.get("email");

		StoreUser storeUser = storeUserRepository.findStoreUserByEmail(email);

		if (storeUser == null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
		}

		emailAuthService.sendOneTimePassword(storeUser);
	}

	@PostMapping("/login/verify")
	public StoreUser verifyLogin(@RequestBody Map<String, String> requestBody)
	{
		String email = requestBody.get("email");
		String oneTimePassword = requestBody.get("oneTimePassword");

		StoreUser storeUser = storeUserRepository.findStoreUserByEmail(email);

		if (checkIfOtpExpired(storeUser.getOtpRequestDate()))
		{
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "One time password has expired");
		}

		if (!passwordEncoder.matches(oneTimePassword, storeUser.getOneTimePassword()))
		{
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "One time password was incorrect");
		}

		return storeUser;
	}

	private boolean checkIfOtpExpired(Date otpRequestDate)
	{
		final long OTP_DURATION = 300000; // five minutes

		long otpExpiration = otpRequestDate.getTime() + OTP_DURATION;

		return otpExpiration < new Date().getTime();
	}

}
