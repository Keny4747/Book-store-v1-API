package pe.todotic.bookstoreapi_s2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.todotic.bookstoreapi_s2.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

//    List<User> findUsersByFirstNameLike(String name);
    @Query("SELECT u FROM User u WHERE u.firstName LIKE  %?1%")
    List<User> findUser(String name);

    Optional<User> findOneByEmail(String email);
}
