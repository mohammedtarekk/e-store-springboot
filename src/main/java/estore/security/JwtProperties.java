package estore.security;

import org.springframework.beans.factory.annotation.Value;

public class JwtProperties {

	public static final String SECRET = "SecretGedn";
	
	@Value("${app.jwtExpirationMs}")
	public static int EXPIRATION_TIME = 8 * 60 * 60 * 1000; // 8 hours
	public static final String TOKEN_TYPE = "Bearer";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	
}
