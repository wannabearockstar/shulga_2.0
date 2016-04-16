package ga.model.repository;

import ga.model.schedule.Auditory;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wannabe on 15.04.16.
 */
public interface AuditoryRepository extends MongoRepository<Auditory, Long> {

}
