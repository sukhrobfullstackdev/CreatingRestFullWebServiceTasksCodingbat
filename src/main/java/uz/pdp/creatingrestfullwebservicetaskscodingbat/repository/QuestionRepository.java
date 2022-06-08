package uz.pdp.creatingrestfullwebservicetaskscodingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
}
