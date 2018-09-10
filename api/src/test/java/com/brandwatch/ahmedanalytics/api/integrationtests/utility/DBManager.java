package com.brandwatch.ahmedanalytics.api.integrationtests.utility;


import com.brandwatch.ahmedanalytics.common.entities.Mention;
import com.brandwatch.ahmedanalytics.common.entities.Query;
import com.brandwatch.ahmedanalytics.common.repositories.MentionsRepository;
import com.brandwatch.ahmedanalytics.common.repositories.QueryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DBManager {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private MentionsRepository mentionsRepository;

    public void populateDB() throws IOException {
        List<Query> queries = mapper.readValue(new File("src/test/resources/testdata/querytestdata.json"), new TypeReference<List<Query>>(){});
        List<Mention> mentions = mapper.readValue(new File("src/test/resources/testdata/mentiontestdata.json"), new TypeReference<List<Mention>>(){});

        queryRepository.saveAll(queries);
        mentionsRepository.saveAll(mentions);
    }

    public void dropDB() {
        queryRepository.deleteAll();
        mentionsRepository.deleteAll();
    }

}
