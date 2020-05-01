<<<<<<< HEAD
package com.fms.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.fms.model.User;

import reactor.core.publisher.Mono;

public interface UserInfoRepository extends ReactiveMongoRepository<User, String>{

	Mono<User> findByusername(String username);
}
=======
package com.fms.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.fms.model.User;

import reactor.core.publisher.Mono;

public interface UserInfoRepository extends ReactiveMongoRepository<User, String>{

	Mono<User> findByusername(String username);
}
>>>>>>> 41f2430933b6c4ce078a731bdcce4bef5c8e6b71
