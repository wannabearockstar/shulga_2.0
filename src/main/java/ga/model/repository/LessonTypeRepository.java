package ga.model.repository;

import ga.model.schedule.LessonType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by wannabe on 15.04.16.
 */
@Repository
public interface LessonTypeRepository extends MongoRepository<LessonType, Long> {

}
