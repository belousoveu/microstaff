package belousov.eu.userservice.repository;

import belousov.eu.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RequestMapping
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLastName(String lastName);

    List<User> findByCompanyId(int companyId);
}
