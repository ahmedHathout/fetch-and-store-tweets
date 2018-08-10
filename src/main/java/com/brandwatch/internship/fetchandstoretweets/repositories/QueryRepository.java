package com.brandwatch.internship.fetchandstoretweets.repositories;

import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QueryRepository extends MongoRepository<Query, Long> {

}
