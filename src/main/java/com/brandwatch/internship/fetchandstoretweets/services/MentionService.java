package com.brandwatch.internship.fetchandstoretweets.services;

import com.brandwatch.internship.fetchandstoretweets.entities.Mention;
import com.brandwatch.internship.fetchandstoretweets.repositories.MentionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentionService {

    private MentionsRepository mentionsRepository;

    @Autowired
    private MentionService(MentionsRepository mentionsRepository) {
        this.mentionsRepository = mentionsRepository;
    }

    public List<Mention> getMentionsByQueryId(long queryId) {
        return mentionsRepository.findById_QueryId(queryId);
    }

}
