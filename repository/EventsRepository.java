package com.fms.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.fms.model.Events;

public interface EventsRepository extends ReactiveMongoRepository<Events, String>{

	
}
