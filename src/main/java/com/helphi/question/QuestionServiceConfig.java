package com.helphi.question;

import com.datastax.oss.driver.api.core.CqlSession;
import com.helphi.question.dao.QuestionDao;
import com.helphi.question.dao.QuestionMapper;
import com.helphi.question.dao.QuestionMapperBuilder;
import java.net.InetSocketAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Spring config. */
@Configuration
public class QuestionServiceConfig {

    private final int dbPort;
    private final String hostname;
    private final String localDatacentre;
    private final String keyspaceName;
    private final String dbUsername;
    private final String dbPassword;

    /** Constructor. Values are passed through spring properties */
    public QuestionServiceConfig(
        @Value("${cassandra.port}") int dbPort, 
        @Value("${cassandra.contact-point}") String hostname,  
        @Value("${cassandra.local-datacentre}") String localDatacentre, 
        @Value("${cassandra.keyspace-name}") String keyspaceName,
        @Value("${cassandra.username}") String dbUsername, 
        @Value("${cassandra.username}") String dbPassword) {     

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
            .withKeyspace(keyspaceName)
            .withAuthCredentials(dbUsername, dbPassword)
            .build();
    }

    public @Bean QuestionDao questionDao() {
        QuestionMapper questionMapper = new QuestionMapperBuilder(this.session()).build();
        return questionMapper.questionDao(keyspaceName);
    }
}
