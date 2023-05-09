package com.gride29.airbnb.clone.backend.repository;

import com.gride29.airbnb.clone.backend.models.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends MongoRepository<Listing, String> {
    List<Listing> findByTitleContaining(String title);
}