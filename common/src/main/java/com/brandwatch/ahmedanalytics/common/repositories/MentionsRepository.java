package com.brandwatch.ahmedanalytics.common.repositories;

import com.brandwatch.ahmedanalytics.common.entities.Mention;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentionsRepository extends MongoRepository<Mention, Long> {

    List<Mention> findById_QueryId(long queryId);

}
