package com.brandwatch.internship.fetchandstoretweets.controllers;

import com.brandwatch.internship.fetchandstoretweets.entities.Mention;
import com.brandwatch.internship.fetchandstoretweets.services.MentionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/mentions", method = RequestMethod.GET)
public class MentionController {

    private MentionService mentionService;

    private MentionController(MentionService mentionService) {
        this.mentionService = mentionService;
    }

    @RequestMapping("/{queryId}")
    public List<Mention> getMentionsByQueryId(@PathVariable("queryId") long queryId) {
        return mentionService.getMentionsByQueryId(queryId);
    }

}
