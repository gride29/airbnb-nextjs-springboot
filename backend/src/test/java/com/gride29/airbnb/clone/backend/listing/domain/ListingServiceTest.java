package com.gride29.airbnb.clone.backend.listing.domain;

import com.gride29.airbnb.clone.backend.TestConfig;
import com.gride29.airbnb.clone.backend.models.Listing;
import com.gride29.airbnb.clone.backend.models.Reservation;
import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import com.gride29.airbnb.clone.backend.repository.ReservationRepository;
import com.gride29.airbnb.clone.backend.security.services.ListingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@ContextConfiguration(classes = TestConfig.class)
public class ListingServiceTest {

    @Autowired
    private ListingService listingService;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        // Clear all listings before each test
        listingRepository.deleteAll();
    }

    @Test
    public void shouldReturnListingsMatchingSearchQuery() throws ParseException {
        String userId = "user123";
        String roomCount = "3";
        String guestCount = "4";
        String bathroomCount = "2";
        String location = "New York";
        String startDate = "2022-01-01T00:00:00.000Z";
        String endDate = "2022-01-10T00:00:00.000Z";
        String category = "Category";

        LocalDateTime createdAt = LocalDateTime.now();
        Listing listing1 = new Listing(
                "Apartment 1",
                "Description 1",
                "Category 1",
                "image1.jpg",
                location,
                userId,
                createdAt,
                Integer.parseInt(guestCount),
                Integer.parseInt(roomCount),
                Integer.parseInt(bathroomCount),
                100
        );
        Listing listing2 = new Listing(
                "Apartment 2",
                "Description 2",
                "Category 2",
                "image2.jpg",
                location,
                "testowyUser",
                createdAt,
                Integer.parseInt(guestCount),
                Integer.parseInt(roomCount),
                Integer.parseInt(bathroomCount),
                150
        );

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        List<Listing> actualListings = listingService.searchListings(userId, roomCount, guestCount, bathroomCount, location, startDate, endDate, category);

        Assertions.assertEquals(1, actualListings.size());
        Assertions.assertEquals(listing1, actualListings.get(0));
    }

    @Test
    public void shouldReturnNoListingsMatchingSearchQuery() throws ParseException {
        String userId = "user123";
        String roomCount = "3";
        String guestCount = "4";
        String bathroomCount = "2";
        String location = "New York";

        LocalDateTime createdAt = LocalDateTime.now();
        Listing listing1 = new Listing(
                "Apartment 1",
                "Description 1",
                "Category 1",
                "image1.jpg",
                location,
                userId,
                createdAt,
                Integer.parseInt(guestCount),
                Integer.parseInt(roomCount),
                Integer.parseInt(bathroomCount),
                100
        );
        Listing listing2 = new Listing(
                "Apartment 2",
                "Description 2",
                "Category 2",
                "image2.jpg",
                location,
                "testowyUser",
                createdAt,
                Integer.parseInt(guestCount),
                Integer.parseInt(roomCount),
                Integer.parseInt(bathroomCount),
                150
        );

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        List<Listing> actualListings = listingService.searchListings("test", "-1", "-1", "-1", "test", null, null, "test");

        Assertions.assertTrue(actualListings.isEmpty());
    }

    @Test
    public void shouldRemoveListingsAndLinkedReservationsByListingId() {
        LocalDateTime createdAt = LocalDateTime.now();
        Listing listing = new Listing(
                "1",
                "Apartment 1",
                "Category 1",
                "image1.jpg",
                "Location 1",
                "User1",
                createdAt,
                2,
                3,
                2,
                100
        );

        listingRepository.save(listing);

        Reservation reservation1 = new Reservation("User2", listing.getId(), LocalDateTime.now(), LocalDateTime.now().plusDays(1), 200, LocalDateTime.now());
        Reservation reservation2 = new Reservation("User3", listing.getId(), LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3), 300, LocalDateTime.now());
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        listingService.deleteById(listing.getId());

        Optional<Listing> deletedListing = listingRepository.findById(listing.getId());
        Assertions.assertTrue(deletedListing.isEmpty(), "Listing should be deleted");

        List<Reservation> remainingReservations = reservationRepository.findByListingId(listing.getId());
        Assertions.assertEquals(0, remainingReservations.size(), "Associated reservations should be deleted");
    }
}