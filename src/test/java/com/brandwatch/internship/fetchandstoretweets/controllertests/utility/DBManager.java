package com.brandwatch.internship.fetchandstoretweets.controllertests.utility;

import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import com.brandwatch.internship.fetchandstoretweets.repositories.MentionsRepository;
import com.brandwatch.internship.fetchandstoretweets.repositories.QueryRepository;
import com.brandwatch.internship.fetchandstoretweets.services.CrawlerJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBManager {

    /**
     * This class is just to insert some date to the database if it is empty and to empty it if there is a need to.
     */

    private MentionsRepository mentionsRepository;
    private QueryRepository queryRepository;
    private CrawlerJob crawlerJob;

    @Autowired
    public DBManager(MentionsRepository mentionsRepository, QueryRepository queryRepository, CrawlerJob crawlerJob) {
        this.mentionsRepository = mentionsRepository;
        this.queryRepository = queryRepository;
        this.crawlerJob = crawlerJob;
    }

    public void deleteAll() {
        mentionsRepository.deleteAll();
        queryRepository.deleteAll();
    }

    public void insert() {
        for (int i = 1; i < 11; i++) {
            queryRepository.save(new Query(i, i + ""));
        }

        crawlerJob.crawl();
    }
}
