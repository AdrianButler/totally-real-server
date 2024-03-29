package adrian.totallyrealserver.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService
{
	private final String SECRET_KEY;

	public JwtService(@Value("${JWT_SECRET_KEY}") String SECRET_KEY)
	{
		this.SECRET_KEY = SECRET_KEY;
	}

	public String extractEmail(String token)
	{
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
	{
		final Claims claims = extractAllClaims(token);

		return claimsResolver.apply(claims);
	}

	public boolean isTokenValid(String token, UserDetails userDetails)
	{
		final String email = extractEmail(token);

		return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(UserDetails userDetails)
	{
		return Jwts.builder()
				.setClaims(new HashMap<>())
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30))) // Token is valid for
				// 30 days
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token)
	{
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token)
	{
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey()
	{
		byte[] keyByteArray = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyByteArray);
	}

}
