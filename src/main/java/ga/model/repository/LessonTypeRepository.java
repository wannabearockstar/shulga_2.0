package ga.model.repository;

import ga.model.schedule.LessonType;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wannabe on 15.04.16.
 */
public interface LessonTypeRepository extends MongoRepository<LessonType, Long> {

}
