package com.brandwatch.internship.fetchandstoretweets.repositories;

import com.brandwatch.internship.fetchandstoretweets.entities.Mention;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MentionsRepository extends MongoRepository<Mention, Long> {

    List<Mention> findById_QueryId(long queryId);

}
