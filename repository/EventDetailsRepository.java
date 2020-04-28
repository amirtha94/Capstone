package com.fms.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.fms.model.EventDetail;

public interface EventDetailsRepository extends ReactiveMongoRepository<EventDetail, String>{

}
