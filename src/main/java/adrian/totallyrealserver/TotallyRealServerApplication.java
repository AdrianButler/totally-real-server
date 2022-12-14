package adrian.totallyrealserver;

import adrian.totallyrealserver.configuration.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebConfig.class)
public class TotallyRealServerApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(TotallyRealServerApplication.class, args);
	}
}
