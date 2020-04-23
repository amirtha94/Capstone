package com.fms.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fms.batch.model.EventDetail;

public interface EventDetailsRepository extends MongoRepository<EventDetail, String>{

}
