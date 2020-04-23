package com.fms.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fms.batch.model.VolunteerDetails;

public interface VolunteerDetailsRepository extends MongoRepository<VolunteerDetails, Integer>{

}
