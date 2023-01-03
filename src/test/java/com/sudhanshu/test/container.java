package com.sudhanshu.test;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.testcontainers.containers.MongoDBContainer;
import org.testng.annotations.Test;

public class container {
    @Test
    public void testConnectToDatabase() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        try (MongoDBContainer mongo = new MongoDBContainer("mongo:4.2.8")) {
            mongo.start();
            String host = mongo.getHost();
            String port = mongo.getMappedPort(27017).toString();
            String database = "mydatabase";
            TestPropertyValues.of(
                    "spring.data.mongodb.host=" + host,
                    "spring.data.mongodb.port=" + port,
                    "spring.data.mongodb.database=" + database
            ).applyTo(context);
            context.register(TestDec2022Application.class);
            context.refresh();
            MongoOperations mongoOps = context.getBean(MongoOperations.class);
            Assertions.assertNotNull(mongoOps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}