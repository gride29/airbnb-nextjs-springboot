package com.gride29.airbnb.clone.backend.repository;

import com.gride29.airbnb.clone.backend.models.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findByUserId(String userId);

    List<Reservation> findByListingId(String listingId);
}
