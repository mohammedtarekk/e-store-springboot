package estore.payload.response;

import java.util.List;

import estore.security.JwtProperties;
import lombok.Data;

@Data
public class JwtResponse {

	 private String token;
	 private String type = JwtProperties.TOKEN_TYPE;
	 private Long id;
	 private String username;
	 private String email;
	 private List<String> authorities;

	 public JwtResponse(String accessToken, Long id, String username, String email, List<String> authorities) {
		 
		    this.token = accessToken;
		    this.id = id;
		    this.username = username;
		    this.email = email;
		    this.authorities = authorities;
		    
	 }
	 
}
