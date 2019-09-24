package com.example.reactiveproducer;

import com.example.reactiveproducer.entity.Valuation;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


@Slf4j
@SpringBootApplication
@EnableR2dbcRepositories
public class ReactiveProducerApplication {

    public static void main(String[] args) {


        new SpringApplicationBuilder(ReactiveProducerApplication.class)
//            .web(WebApplicationType.REACTIVE)
            .run();


        ConnectionFactory connectionFactory = ConnectionFactories.get("rdbc:h2:mem:///test?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

        DatabaseClient client = DatabaseClient.create(connectionFactory);
        client.select()
            .from(Valuation.class)
            .fetch()
            .first()
            .doOnNext(it -> log.info(it))
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete();
    }

    @Bean
    public PostgresqlConnectionFactory connectionFactory() {
          PostgresqlConnectionFactory connectionFactory = new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
            .host("localhost")
            .database("postgres")
            .username("postgres")
            .password("postgres").build());

          return connectionFactory;

    }

    @Bean
    public DatabaseClient databaseClient()  {
        return DatabaseClient.create(connectionFactory());
    }

    @Bean
    public ReactiveDataAccessStrategy reactiveDataAccessStrategy()  {
        DefaultReactiveDataAccessStrategy defaultReactiveDataAccessStrategy = new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE);
        return defaultReactiveDataAccessStrategy;
    }

}
