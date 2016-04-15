package ga.model.repository;

import ga.model.schedule.Discipline;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by wannabe on 15.04.16.
 */
@Repository
public interface DisciplineRepository extends MongoRepository<Discipline, Long> {

}
