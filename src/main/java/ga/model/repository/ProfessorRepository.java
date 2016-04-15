package ga.model.repository;

import ga.model.schedule.Professor;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wannabe on 15.04.16.
 */
public interface ProfessorRepository extends MongoRepository<Professor, Long> {

}
