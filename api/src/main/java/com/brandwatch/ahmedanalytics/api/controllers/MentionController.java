package com.brandwatch.ahmedanalytics.api.controllers;

import com.brandwatch.ahmedanalytics.common.entities.Mention;
import com.brandwatch.ahmedanalytics.common.services.MentionService;
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
