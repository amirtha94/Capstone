package com.fms.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fms.batch.model.Events;

public interface EventRepository extends MongoRepository<Events, String>{

}
