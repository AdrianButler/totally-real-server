package adrian.totallyrealserver.services;

import adrian.totallyrealserver.models.StoreUser;
import adrian.totallyrealserver.repositories.StoreUserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;

@Service
public class EmailAuthService
{
	@Autowired
	JavaMailSender mailSender;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	StoreUserRepository storeUserRepository;

	@Value("${MAIL_USERNAME}")
	private String fromAddress;

	public void sendOneTimePassword(StoreUser storeUser)
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

	private void saveOneTimePassword(StoreUser storeUser, String oneTimePassword)
	{
		String encodedOneTimePassword = passwordEncoder.encode(oneTimePassword);

		storeUser.setOneTimePassword(encodedOneTimePassword);
		storeUser.setOtpRequestTime(new Date());

		storeUserRepository.save(storeUser);
	}
}
