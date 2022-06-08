package uz.pdp.creatingrestfullwebservicetaskscodingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
