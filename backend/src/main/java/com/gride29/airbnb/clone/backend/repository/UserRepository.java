package com.gride29.airbnb.clone.backend.repository;

import java.util.Optional;

import com.gride29.airbnb.clone.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}