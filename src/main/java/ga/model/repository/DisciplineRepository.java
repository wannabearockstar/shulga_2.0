package ga.model.repository;

import ga.model.schedule.Discipline;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wannabe on 15.04.16.
 */
public interface DisciplineRepository extends MongoRepository<Discipline, Long> {

}
