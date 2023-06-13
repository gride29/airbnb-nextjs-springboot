package com.gride29.airbnb.clone.backend.user.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gride29.airbnb.clone.backend.TestConfig;
import com.gride29.airbnb.clone.backend.models.Listing;
import com.gride29.airbnb.clone.backend.models.User;
import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import com.gride29.airbnb.clone.backend.repository.ReservationRepository;
import com.gride29.airbnb.clone.backend.repository.UserRepository;
import com.gride29.airbnb.clone.backend.security.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        // Clear all things before each test
        userRepository.deleteAll();
        reservationRepository.deleteAll();
        listingRepository.deleteAll();
    }

    @Test
    public void shouldReturnFavoriteListingsForUser() throws JsonProcessingException {
        User user = new User("User1", "user1@example.com", "password123");
        userRepository.save(user);

        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        user.setFavoriteListings(Arrays.asList(listing1.getId(), listing2.getId()).toArray(new String[0]));
        userRepository.save(user);

        List<Listing> favoriteListings = userService.getFavoriteListings(user.getId());

        Assertions.assertEquals(2, favoriteListings.size());
        Assertions.assertTrue(favoriteListings.contains(listing1));
        Assertions.assertTrue(favoriteListings.contains(listing2));
    }

    @Test
    public void shouldAddFavoriteListingForUser() throws JsonProcessingException {
        User user = new User("User1", "user1@example.com", "password123");
        userRepository.save(user);

        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        listingRepository.save(listing1);

        String jsonPayloadListing = "{"
                + "\"favoriteListing\": \"" + listing1.getId() + "\""
                + "}";

        String addedListing = userService.addFavoriteListing(user.getId(), jsonPayloadListing);

        User updatedUser = userRepository.findById(user.getId()).orElse(null);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertTrue(Arrays.asList(updatedUser.getFavoriteListings()).contains(listing1.getId()));
        Assertions.assertEquals(listing1.getId(), addedListing);
    }

    @Test
    public void shouldRemoveFavoriteListingForUser() throws JsonProcessingException {
        User user = new User("User1", "user1@example.com", "password123");
        userRepository.save(user);

        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        user.setFavoriteListings(Arrays.asList(listing1.getId(), listing2.getId()).toArray(new String[0]));
        userRepository.save(user);

        String jsonPayloadListing = "{"
                + "\"favoriteListing\": \"" + listing1.getId() + "\""
                + "}";

        String removedListing = userService.removeFavoriteListing(user.getId(), jsonPayloadListing);

        List<Listing> favoriteListings = userService.getFavoriteListings(user.getId());

        Assertions.assertEquals(1, favoriteListings.size());
        Assertions.assertFalse(favoriteListings.contains(listing1));
        Assertions.assertTrue(favoriteListings.contains(listing2));
        Assertions.assertEquals(listing1.getId(), removedListing);
    }

    private List<Listing> createTestListings() {
        Listing listing1 = new Listing(
                "Cool place",
                "Welcome to my place",
                "Windmills",
                "testImg.png",
                "AF",
                "1",
                null,
                1,
                1,
                1,
                50
        );

        Listing listing2 = new Listing(
                "Cool place 2",
                "Welcome to my place 2",
                "Pools",
                "testImg2.png",
                "FR",
                "2",
                null,
                1,
                1,
                1,
                100
        );

        return Arrays.asList(listing1, listing2);
    }
}