package ga.model.repository;

import ga.model.schedule.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wannabe on 15.04.16.
 */
public interface ScheduleRepository extends MongoRepository<Schedule, String> {

}
