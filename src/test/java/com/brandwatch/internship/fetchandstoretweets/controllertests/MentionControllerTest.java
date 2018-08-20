package com.brandwatch.internship.fetchandstoretweets.controllertests;

import com.brandwatch.internship.fetchandstoretweets.controllers.MentionController;
import com.brandwatch.internship.fetchandstoretweets.entities.Mention;
import com.brandwatch.internship.fetchandstoretweets.repositories.MentionsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class MentionControllerTest {

    private static final long QUERY_ID = 3;


    @Autowired
    private MentionController mentionController;

    @Autowired
    private MentionsRepository mentionsRepository;


    @Test
    public void getMentionsByQueryIdTestNormal() {
        List<Mention> mentions = mentionController.getMentionsByQueryId(QUERY_ID);
        assertEquals(mentions.size(), mentionsRepository.findById_QueryId(QUERY_ID).size());
    }

}
