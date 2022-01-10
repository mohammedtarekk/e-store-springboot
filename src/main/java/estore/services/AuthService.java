package estore.services;

import estore.payload.request.LoginRequest;
import estore.payload.request.SignupRequest;
import estore.payload.response.APIResponse;
import estore.payload.response.JwtResponse;

public interface AuthService {

	public JwtResponse authenticateUser(LoginRequest loginRequest);
	
	public APIResponse registerUser(SignupRequest signUpRequest);

	public APIResponse deleteUser(Long userId);
	
}
