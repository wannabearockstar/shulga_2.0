package ga.model.repository;

import ga.model.schedule.time.DayTime;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wannabe on 15.04.16.
 */
public interface DayTimeRepository extends MongoRepository<DayTime, Integer> {

}
