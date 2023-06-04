package com.gride29.airbnb.clone.backend.security.services;

import com.gride29.airbnb.clone.backend.models.Reservation;
import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import com.gride29.airbnb.clone.backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.gride29.airbnb.clone.backend.models.Listing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ListingService {

    private final MongoOperations mongoOperations;

    @Autowired
    public ListingService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    ReservationRepository reservationRepository;

    public List<Listing> searchListings(String userId, String roomCount, String guestCount,
                                        String bathroomCount, String location,
                                        String startDate, String endDate, String category) throws ParseException {
        Query query = new Query();

        if (userId != null) {
            query.addCriteria(Criteria.where("userId").is(userId));
        }

        if (category != null) {
            Pattern regexPattern = Pattern.compile(category, Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("category").regex(regexPattern));
        }

        if (roomCount != null) {
            query.addCriteria(Criteria.where("roomCount").gte(Integer.parseInt(roomCount)));
        }

        if (guestCount != null) {
            query.addCriteria(Criteria.where("guestCount").gte(Integer.parseInt(guestCount)));
        }

        if (bathroomCount != null) {
            query.addCriteria(Criteria.where("bathroomCount").gte(Integer.parseInt(bathroomCount)));
        }

        if (location != null) {
            query.addCriteria(Criteria.where("location").is(location));
        }

        if (startDate != null && endDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date parsedStartDate = dateFormat.parse(startDate);
            Date parsedEndDate = dateFormat.parse(endDate);

            Criteria reservationsCriteria = new Criteria().andOperator(
                    Criteria.where("startDate").lt(parsedEndDate),
                    Criteria.where("endDate").gt(parsedStartDate)
            );

            Query reservationsQuery = new Query();
            reservationsQuery.addCriteria(reservationsCriteria);

            List<Reservation> reservations = mongoOperations.find(reservationsQuery, Reservation.class, "reservation");
            List<String> listingIds = new ArrayList<>();
            for (Reservation reservation : reservations) {
                listingIds.add(reservation.getListingId());
            }
            query.addCriteria(Criteria.where("id").nin(listingIds));
        }

        return mongoOperations.find(query, Listing.class, "listing");
    }

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
