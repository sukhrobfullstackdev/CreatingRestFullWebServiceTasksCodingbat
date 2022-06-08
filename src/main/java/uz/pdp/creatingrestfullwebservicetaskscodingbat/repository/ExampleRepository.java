package uz.pdp.creatingrestfullwebservicetaskscodingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Example;

@Repository
public interface ExampleRepository extends JpaRepository<Example,Integer> {
}
