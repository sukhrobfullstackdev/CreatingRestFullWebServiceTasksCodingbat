package uz.pdp.creatingrestfullwebservicetaskscodingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmailOrPassword(String email, String password);
    boolean existsByEmailOrPasswordAndIdNot(String email, String password, Integer id);
}
