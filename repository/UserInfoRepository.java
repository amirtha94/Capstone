package com.fms.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.fms.model.User;

import reactor.core.publisher.Mono;

public interface UserInfoRepository extends ReactiveMongoRepository<User, String>{

	Mono<User> findByusername(String username);
}
