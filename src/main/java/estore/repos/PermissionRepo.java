package estore.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import estore.models.Permission;

public interface PermissionRepo extends JpaRepository<Permission, Long>{

	Optional<Permission> findById(Long id);
	
	Optional<Permission> findByName(String name);
	
}
