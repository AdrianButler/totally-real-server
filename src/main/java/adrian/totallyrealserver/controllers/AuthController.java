package adrian.totallyrealserver.controllers;

import adrian.totallyrealserver.models.StoreUser;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import adrian.totallyrealserver.services.EmailAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController
{
	final StoreUserRepository storeUserRepository;

	final EmailAuthService emailAuthService;

	public AuthController(StoreUserRepository storeUserRepository, EmailAuthService emailAuthService)
	{
		this.storeUserRepository = storeUserRepository;
		this.emailAuthService = emailAuthService;
	}

	@PostMapping("/signup")
	public StoreUser createUser(@RequestBody StoreUser user)
	{
		StoreUser userFromSearch = storeUserRepository.findStoreUserByEmail(user.getEmail());

		if (userFromSearch != null) // if user already exists return that user
		{
			return userFromSearch;
		}

		StoreUser newUser = new StoreUser(user.getName(), user.getEmail());
		storeUserRepository.save(newUser);

		return newUser;
	}

	@PostMapping("/login")
	public StoreUser loginUser(@RequestBody StoreUser user) // TODO implement a real auth system
	{
		return storeUserRepository.findStoreUserByEmail(user.getEmail());
	}
}
