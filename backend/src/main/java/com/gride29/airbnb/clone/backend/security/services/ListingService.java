package com.gride29.airbnb.clone.backend.security.services;

import com.gride29.airbnb.clone.backend.models.Reservation;
import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import com.gride29.airbnb.clone.backend.repository.ReservationRepository;
import com.gride29.airbnb.clone.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gride29.airbnb.clone.backend.models.Listing;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListingService {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    ReservationRepository reservationRepository;

    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    public List<Listing> findByTitleContaining(String title) {
        return listingRepository.findByTitleContaining(title);
    }

    public Optional<Listing> findById(String id) {
        return listingRepository.findById(id);
    }

    public List<Listing> findByUserId(String userId) {
        return listingRepository.findByUserId(userId);
    }

    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }

    public Listing update(String id, Listing listing) {
        Optional<Listing> optionalListing = listingRepository.findById(id);
        if (optionalListing.isPresent()) {
            listing.setId(id);
            return listingRepository.save(listing);
        } else {
            return null;
        }
    }

    public void deleteById(String id) {
        // Fetch the listing by ID
        Optional<Listing> optionalListing = listingRepository.findById(id);
        if (optionalListing.isPresent()) {
            Listing listing = optionalListing.get();

            // Retrieve reservations associated with the listing
            List<Reservation> reservations = reservationRepository.findByListingId(listing.getId());

            // Extract reservation IDs
            List<String> reservationIds = reservations.stream()
                    .map(Reservation::getId)
                    .collect(Collectors.toList());

            // Delete the reservations by IDs
            reservationRepository.deleteByIdIn(reservationIds);

            // Delete the listing
            listingRepository.deleteById(id);
        } else {
            // Handle case when the listing is not found
            throw new IllegalArgumentException("Listing not found with ID: " + id);
        }
    }

    public void deleteAll() {
        listingRepository.deleteAll();
    }
}
