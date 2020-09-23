package utn.poc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.poc.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}
