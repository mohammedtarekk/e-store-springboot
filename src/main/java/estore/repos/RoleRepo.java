package estore.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import estore.models.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{

	Optional<Role> findById(Long id);
	
	Optional<Role> findByName(String name);
	
}
