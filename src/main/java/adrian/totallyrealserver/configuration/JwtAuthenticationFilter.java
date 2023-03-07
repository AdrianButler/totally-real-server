package adrian.totallyrealserver.configuration;

import adrian.totallyrealserver.repositories.StoreUserRepository;
import adrian.totallyrealserver.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	private final JwtService jwtService;
	private final StoreUserRepository storeUserRepository;

	public JwtAuthenticationFilter(JwtService jwtService, StoreUserRepository storeUserRepository)
	{
		this.jwtService = jwtService;
		this.storeUserRepository = storeUserRepository;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException
	{
		final String authHeader = request.getHeader("Authorization");
		final String jsonWebToken;
		final String email;

		if (authHeader == null || !authHeader.startsWith("Bearer "))
		{
			filterChain.doFilter(request, response);
			return;
		}

		jsonWebToken = authHeader.substring(7); // index 7 because token starts with "Bearer "
		email = jwtService.extractEmail(jsonWebToken);

		if (email != null && SecurityContextHolder.getContext()
				.getAuthentication() == null)
		{
			UserDetails userDetails = storeUserRepository.findStoreUserByEmail(email);

			if (jwtService.isTokenValid(jsonWebToken, userDetails))
			{
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}

		filterChain.doFilter(request, response);


	}
}
