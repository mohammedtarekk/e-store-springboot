package estore.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RUNTIME)
@Target(METHOD) // can be class or method or both or many other things
@PreAuthorize("hasRole('ADMIN')")
public @interface IsAdmin {
	// this is an example for meta-annotation security
	// Security meta-annotations are a great idea because they add more semantics 
	// and decouple our business logic from the security framework
	
}
