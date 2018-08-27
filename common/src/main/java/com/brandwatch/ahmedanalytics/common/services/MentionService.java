package com.brandwatch.ahmedanalytics.common.services;

import com.brandwatch.ahmedanalytics.common.repositories.MentionsRepository;
import com.brandwatch.ahmedanalytics.common.entities.Mention;
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

    public void saveMentions(List<Mention> mentions) {
        mentionsRepository.saveAll(mentions);
    }
}
