package com.brandwatch.internship.fetchandstoretweets.repositories;

import com.brandwatch.internship.fetchandstoretweets.entities.Mention;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MentionsRepository extends MongoRepository<Mention, Long> {

}
