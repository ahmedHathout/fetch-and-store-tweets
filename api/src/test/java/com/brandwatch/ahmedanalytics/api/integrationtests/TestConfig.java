package com.brandwatch.ahmedanalytics.api.integrationtests;

import com.brandwatch.ahmedanalytics.api.integrationtests.utility.DBManager;
import com.mongodb.MongoClient;
import com.palantir.docker.compose.DockerComposeRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class TestConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.database}") private String mongoDB;

    @Bean(initMethod = "populateDB", destroyMethod = "dropDB")
    public DBManager dbManager() {
        return new DBManager();
    }


    @Bean(initMethod = "before", destroyMethod = "after")
    public DockerComposeRule dockerComposeRule() {
        return new DockerComposeRule
                .Builder()
                .file("src/test/resources/docker-compose.yml")
                .saveLogsTo("target/dockerLogs/dockerComposeRuleTest")
                .build();
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("localhost",
                dockerComposeRule()
                        .containers()
                        .container("mongo")
                        .port(27017)
                        .getExternalPort());
    }

    @Override
    protected String getDatabaseName() {
        return mongoDB;
    }
}
