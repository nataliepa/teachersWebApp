package gr.aueb.elearn.teachersWebApp.repositories;

import gr.aueb.elearn.teachersWebApp.model.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TeacherRepository extends CrudRepository <Teacher, Integer>, PagingAndSortingRepository <Teacher, Integer> {
    List<Teacher> findBySnameStartsWith(String surname);
}
