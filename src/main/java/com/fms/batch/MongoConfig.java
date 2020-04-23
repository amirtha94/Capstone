
package com.fms.batch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig   {

	//MongoClientOptions mo = MongoClientOptions.builder().sslEnabled(false).build();
	@Bean
    public MongoClient mongo() {
        return   MongoClients.create("mongodb://admin:admin@localhost:27017/fms_event");
        		//(new ServerAddress("localhost", 27017),MongoCredential.createCredential("admin","fms_event","admin".toCharArray()),mo);
    }
 
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "fms_event");
    }

	
}
