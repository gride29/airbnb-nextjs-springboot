package com.gride29.airbnb.clone.backend.repository;

import java.util.Optional;

import com.gride29.airbnb.clone.backend.models.ERole;
import com.gride29.airbnb.clone.backend.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}