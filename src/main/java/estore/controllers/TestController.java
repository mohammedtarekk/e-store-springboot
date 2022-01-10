package estore.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import estore.annotations.IsAdmin;

@RestController
@RequestMapping("/api/test")
public class TestController {

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")  // elly bymyzha 3n el b2yen en bytktb feha SpEL
	// @RolesAllowed("ROLE_VIEWER")  equivalent to  @Secured("ROLE_VIEWER")
	// @RolesAllowed({ "ROLE_VIEWER", "ROLE_EDITOR" })  equivalent to  @Secured({ "ROLE_VIEWER", "ROLE_EDITOR" })
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	// @PostAuthorize annotation provides the ability to access the method result:
	// @PostAuthorize("returnObject.username == authentication.principal.nickName")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	//@PreAuthorize("hasRole('ADMIN')") // here spring adds ROLE_ prefix to the given value by default
	//@PreAuthorize("#username == authentication.principal.username")  // mmkn a7ot feha el params kman
	@IsAdmin
	public String adminAccess(String username) {
		return "Admin Board.";
	}
	
	@PreAuthorize("#username == authentication.principal.username")
	@PostAuthorize("returnObject.username == authentication.principal.nickName")
	// security will validate both pre and post
	public Object securedLoadUserDetail(String username) {
	    return new Object();
	}
	
}
