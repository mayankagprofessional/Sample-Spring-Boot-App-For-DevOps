package info.mayankag.springscale.user.repository;

import info.mayankag.springscale.user.domain.User;
import info.mayankag.springscale.user.domain.UserOutputDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.age >= ?1 AND u.age <= ?2")
    List<User> findByAgeGroup(Integer startAge, Integer endAge);
}
