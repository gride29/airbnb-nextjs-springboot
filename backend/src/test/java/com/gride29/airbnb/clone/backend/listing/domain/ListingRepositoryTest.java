package com.gride29.airbnb.clone.backend.listing.domain;

import com.gride29.airbnb.clone.backend.TestConfig;
import com.gride29.airbnb.clone.backend.models.Listing;
import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@ContextConfiguration(classes = TestConfig.class)
public class ListingRepositoryTest {

    @Autowired
    private ListingRepository listingRepository;

    @BeforeEach
    public void setUp() {
        // Clear all listings before each test
        listingRepository.deleteAll();
    }

    @Test
    public void shouldReturnMatchingListingsWithTitleContaining() {
        LocalDateTime createdAt = LocalDateTime.now();
        Listing listing1 = new Listing(
                "Apartment 1",
                "Description 1",
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
        Listing listing2 = new Listing(
                "Apartment 2",
                "Description 2",
                "Category 2",
                "image2.jpg",
                "Location 2",
                "User2",
                createdAt,
                4,
                2,
                1,
                150
        );
        listingRepository.save(listing1);
        listingRepository.save(listing2);

        List<Listing> actualListings = listingRepository.findByTitleContaining("Apartment");

        Assertions.assertEquals(2, actualListings.size());
        Assertions.assertTrue(actualListings.containsAll(Arrays.asList(listing1, listing2)));
    }

    @Test
    public void shouldNotReturnMatchingListingsWithTitleContaining() {
        LocalDateTime createdAt = LocalDateTime.now();
        Listing listing1 = new Listing(
                "Apartment 1",
                "Description 1",
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
        Listing listing2 = new Listing(
                "Apartment 2",
                "Description 2",
                "Category 2",
                "image2.jpg",
                "Location 2",
                "User2",
                createdAt,
                4,
                2,
                1,
                150
        );
        listingRepository.save(listing1);
        listingRepository.save(listing2);

        List<Listing> actualListings = listingRepository.findByTitleContaining("InvalidTitle");

        Assertions.assertTrue(actualListings.isEmpty());
    }

    @Test
    public void shouldReturnMatchingListingsWithMatchingUserId() {
        LocalDateTime createdAt = LocalDateTime.now();
        Listing listing1 = new Listing(
                "Apartment 1",
                "Description 1",
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
        Listing listing2 = new Listing(
                "Apartment 2",
                "Description 2",
                "Category 2",
                "image2.jpg",
                "Location 2",
                "User2",
                createdAt,
                4,
                2,
                1,
                150
        );
        listingRepository.save(listing1);
        listingRepository.save(listing2);

        List<Listing> actualListings = listingRepository.findByUserId("User1");

        Assertions.assertEquals(1, actualListings.size());
        Assertions.assertTrue(actualListings.contains(listing1));
    }

    @Test
    public void shouldNotReturnMatchingListingsWithMatchingUserId() {
        LocalDateTime createdAt = LocalDateTime.now();
        Listing listing1 = new Listing(
                "Apartment 1",
                "Description 1",
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
        Listing listing2 = new Listing(
                "Apartment 2",
                "Description 2",
                "Category 2",
                "image2.jpg",
                "Location 2",
                "User2",
                createdAt,
                4,
                2,
                1,
                150
        );

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        List<Listing> actualListings = listingRepository.findByUserId("InvalidUserId");

        Assertions.assertTrue(actualListings.isEmpty());
    }
}