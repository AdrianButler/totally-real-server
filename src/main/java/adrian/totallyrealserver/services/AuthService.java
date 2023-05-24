package adrian.totallyrealserver.services;

import adrian.totallyrealserver.dtos.auth.SignUpRequest;
import adrian.totallyrealserver.exceptions.UserAlreadyExistsException;
import adrian.totallyrealserver.models.StoreUser;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{
	final JavaMailSender mailSender;

	final PasswordEncoder passwordEncoder;

	final StoreUserRepository storeUserRepository;

	@Value("${MAIL_USERNAME}")
	private String fromAddress;

	public AuthService(JavaMailSender mailSender, PasswordEncoder passwordEncoder, StoreUserRepository storeUserRepository)
	{
		this.mailSender = mailSender;
		this.passwordEncoder = passwordEncoder;
		this.storeUserRepository = storeUserRepository;
	}

	public void createUser(SignUpRequest signUpRequest) throws UserAlreadyExistsException
	{
		String name = signUpRequest.getName();
		String email = signUpRequest.getEmail();

		if (storeUserRepository.existsByEmail(email)) // check if user with email already exists
		{
			throw new UserAlreadyExistsException();
		}

		StoreUser newUser = new StoreUser(name, email);
		storeUserRepository.save(newUser);

		sendOneTimePassword(newUser);
	}

	private void sendOneTimePassword(StoreUser storeUser)
	{
		String oneTimePassword = RandomString.make(8);

		saveOneTimePassword(storeUser, oneTimePassword); // encode and save otp to db

		final String subject = "Your one time code";
		final String content = "Use this code to continue signing in: " + oneTimePassword;
		final String fromName = "Totally Real Shopping";
		final String toAddress = storeUser.getEmail();

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try
		{
			helper.setFrom(fromAddress, fromName);
			helper.setTo(toAddress);
			helper.setSubject(subject);
			helper.setText(content);
		} catch (MessagingException | UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}

		mailSender.send(message);
	}

	public boolean checkIfOtpExpired(Date otpRequestDate)
	{
		final long OTP_DURATION = 300000; // five minutes

		long otpExpiration = otpRequestDate.getTime() + OTP_DURATION;

		return otpExpiration < new Date().getTime();
	}

	private void saveOneTimePassword(StoreUser storeUser, String oneTimePassword)
	{
		String encodedOneTimePassword = passwordEncoder.encode(oneTimePassword);

		storeUser.setOneTimePassword(encodedOneTimePassword);
		storeUser.setOtpRequestDate(new Date());

		storeUserRepository.save(storeUser);
	}

}
