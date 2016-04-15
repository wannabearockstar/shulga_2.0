package ga.model.repository;

import ga.model.schedule.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wannabe on 15.04.16.
 */
public interface GroupRepository extends MongoRepository<Group, Long> {

}
