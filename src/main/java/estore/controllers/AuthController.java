package estore.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import estore.payload.request.LoginRequest;
import estore.payload.request.SignupRequest;
import estore.payload.response.APIResponse;
import estore.services.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.authenticateUser(loginRequest));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {	
		APIResponse response = authService.registerUser(signUpRequest);	
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@DeleteMapping("/deleteuser")
	public ResponseEntity<?> deleteUser(@RequestParam(required = true) Long userId) {	
		APIResponse response = authService.deleteUser(userId);	
		return new ResponseEntity<>(response, response.getStatus());
	}
	
}
