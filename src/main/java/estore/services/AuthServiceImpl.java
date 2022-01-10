package estore.services;

import static java.time.LocalDateTime.now;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import estore.models.Role;
import estore.models.User;
import estore.payload.request.LoginRequest;
import estore.payload.request.SignupRequest;
import estore.payload.response.APIResponse;
import estore.payload.response.JwtResponse;
import estore.repos.RoleRepo;
import estore.repos.UserRepo;
import estore.security.JwtUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final UserRepo userRepo;
	private final RoleRepo roleRepo;	
	private final PasswordEncoder encoder;

	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Override
	public JwtResponse authenticateUser(LoginRequest loginRequest) {
				
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		User user = (User) authentication.getPrincipal();		
		List<String> authorities = user.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail(), authorities);
	}

	@Override
	public APIResponse registerUser(SignupRequest signUpRequest) {
				
		if(userRepo.existsByUsername(signUpRequest.getUsername())) {			
			return APIResponse.builder()
								  .timestamp(now())
								  .clientMessage("Error: Username is already taken!")
								  .status(HttpStatus.BAD_REQUEST)
								  .statusCode(HttpStatus.BAD_REQUEST.value())
								  .build();
		}
		
		if(userRepo.existsByEmail(signUpRequest.getEmail())) {			
			return APIResponse.builder()
							 	  .timestamp(now())
							 	  .clientMessage("Error: Email is already taken!")
							 	  .status(HttpStatus.BAD_REQUEST)
								  .statusCode(HttpStatus.BAD_REQUEST.value())
							 	  .build();
		}
				
		// create new user
		User user = User.builder()
						.username(signUpRequest.getUsername())
						.password(encoder.encode(signUpRequest.getPassword()))
						.email(signUpRequest.getEmail())
						.locked(false)
						.enabled(false)
						.expired(false)
				        .build();
		
		Set<String> rolesStr = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		
		if(rolesStr == null) {
			Role userRole = roleRepo.findByName("ROLE_USER")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		}
		else {
			rolesStr.forEach(role -> {
				
				Role userRole = roleRepo.findByName(role)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
				
			});
		}
		
		user.setRoles(roles);
		userRepo.save(user);
		
		return APIResponse.builder()
			 	  .timestamp(now())
			 	  .clientMessage("User registered successfully!")
			 	  .status(HttpStatus.CREATED)
				  .statusCode(HttpStatus.CREATED.value())
				  .body(user)
			 	  .build();
		
	}

	@Override
	public APIResponse deleteUser(Long userId) {
		
		try {
			
			userRepo.deleteById(userId);
			
		} catch(EmptyResultDataAccessException e) {

			logger.error("Error: {}", e.getMessage());

			return APIResponse.builder()
				 	  .timestamp(now())
				 	  .clientMessage("User with id: " + userId + " does not exist!")
				 	  .status(HttpStatus.NOT_FOUND)
					  .statusCode(HttpStatus.NOT_FOUND.value())
				 	  .build();
			
		}
		
		return APIResponse.builder()
			 	  .timestamp(now())
			 	  .clientMessage("User with id: " + userId + " has been deleted successfully!")
			 	  .status(HttpStatus.OK)
				  .statusCode(HttpStatus.OK.value())
			 	  .build();
	}

	
}
