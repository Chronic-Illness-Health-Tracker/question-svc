package com.helphi.question;

import java.net.InetSocketAddress;

import com.datastax.oss.driver.internal.core.type.codec.extras.enums.EnumNameCodec;
import com.helphi.question.api.QuestionType;
import com.helphi.question.dao.*;
import com.helphi.question.svc.id.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.session.Session;

/** Spring config. */
@Configuration
public class QuestionServiceConfig  {

    private final int dbPort;
    private final String hostname;
    private final String localDatacentre;
    private final String keyspaceName;
    private final String dbUsername;
    private final String dbPassword;

    public QuestionServiceConfig(
        @Value("${db.port}") int dbPort, 
        @Value("${db.contact-point}") String hostname,  
        @Value("${db.local-datacenter}") String localDatacentre, 
        @Value("${db.keyspace-name}") String keyspaceName,
        @Value("${db.username}") String dbUsername, 
        @Value("${db.password}") String dbPassword) {

        this.dbPort = dbPort;
        this.hostname = hostname;
        this.localDatacentre = localDatacentre;
        this.keyspaceName = keyspaceName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    /**
    * Use the standard Cassandra driver API to create a 
    * com.datastax.oss.driver.api.core.CqlSession instance.
    */
    public @Bean CqlSession session() {
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(hostname, dbPort))
                .withLocalDatacenter(localDatacentre)
                .withAuthCredentials(dbUsername, dbPassword)
                .addTypeCodecs(new EnumNameCodec<QuestionType>(QuestionType.class))
                .build();
    }

    public @Bean QuestionDao questionDao() {
        QuestionMapper questionMapper = new QuestionMapperBuilder(this.session()).build();
        return questionMapper.questionDao(keyspaceName);
    }

    public @Bean UserResponseDao userResponseDao() {
        QuestionMapper questionMapper = new QuestionMapperBuilder(this.session()).build();
        return questionMapper.userResponseDao(keyspaceName);
    }

    public @Bean ConditionCheckInDao conditionCheckInDao() {
        QuestionMapper questionMapper = new QuestionMapperBuilder(this.session()).build();
        return questionMapper.conditionCheckInDao(keyspaceName);
    }

    public @Bean Snowflake snowflake() {
        return new Snowflake(1);
    }
}
