package ga.model.repository;

import ga.model.config.ScheduleConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wannabe on 15.04.16.
 */
public interface ScheduleConfigRepository extends MongoRepository<ScheduleConfig, String> {

}
