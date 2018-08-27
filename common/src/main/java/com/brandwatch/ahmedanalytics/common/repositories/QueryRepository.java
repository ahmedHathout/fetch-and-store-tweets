package com.brandwatch.ahmedanalytics.common.repositories;

import com.brandwatch.ahmedanalytics.common.entities.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRepository extends MongoRepository<Query, Long> {

    Query findFirstByOrderByIdDesc();

    Query findOneById(long id);
}
