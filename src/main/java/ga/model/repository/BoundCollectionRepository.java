package ga.model.repository;

import ga.model.bound.BoundCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by wannabe on 16.04.16.
 */
public interface BoundCollectionRepository extends MongoRepository<BoundCollection, String> {

}
