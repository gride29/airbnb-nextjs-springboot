package com.gride29.airbnb.clone.backend.user.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gride29.airbnb.clone.backend.TestWebConfig;
import com.gride29.airbnb.clone.backend.controllers.ListingController;
import com.gride29.airbnb.clone.backend.models.Listing;
import com.gride29.airbnb.clone.backend.models.User;
import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import com.gride29.airbnb.clone.backend.repository.UserRepository;
import com.gride29.airbnb.clone.backend.security.services.ListingService;
import com.gride29.airbnb.clone.backend.security.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ListingController.class)
@ContextConfiguration(classes = TestWebConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ListingService listingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        listingRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldReturnFavoriteListingsForUser() throws Exception {
        User user = new User("User1", "user1@example.com", "password123");
        userRepository.save(user);

        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        user.setFavoriteListings(Arrays.asList(listing1.getId(), listing2.getId()).toArray(new String[0]));
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/favorites/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.favoriteListings[0].id").value(listing1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.favoriteListings[1].id").value(listing2.getId()));
    }

    @Test
    public void shouldAddFavoriteListingForUser() throws Exception {
        User user = new User("User1", "user1@example.com", "password123");
        userRepository.save(user);

        List<Listing> testListings = createTestListings();
        Listing listing = testListings.get(0);
        listingRepository.save(listing);

        String jsonPayload = "{\"favoriteListing\": \"" + listing.getId() + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/favorites/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addedListing").value(listing.getId()));
    }

    @Test
    public void shouldReturnConflictWhenAddingAlreadyAddedFavoriteListing() throws Exception {
        User user = new User("User1", "user1@example.com", "password123");
        userRepository.save(user);

        List<Listing> testListings = createTestListings();
        Listing listing = testListings.get(0);
        listingRepository.save(listing);

        user.setFavoriteListings(new String[]{listing.getId()});
        userRepository.save(user);

        String jsonPayload = "{\"favoriteListing\": \"" + listing.getId() + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/favorites/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Listing already added."));
    }

    @Test
    public void shouldRemoveFavoriteListingForUser() throws Exception {
        User user = new User("User1", "user1@example.com", "password123");
        userRepository.save(user);

        List<Listing> testListings = createTestListings();
        Listing listing = testListings.get(0);
        listingRepository.save(listing);

        user.setFavoriteListings(new String[]{listing.getId()});
        userRepository.save(user);

        String jsonPayload = "{\"favoriteListing\": \"" + listing.getId() + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/favorites/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.removedListing").value(listing.getId()));
    }

    @Test
    public void shouldReturnNotFoundWhenRemovingNonexistentFavoriteListing() throws Exception {
        User user = new User("User1", "user1@example.com", "password123");
        userRepository.save(user);

        List<Listing> testListings = createTestListings();
        Listing listing = testListings.get(0);
        listingRepository.save(listing);

        String jsonPayload = "{\"favoriteListing\": \"" + listing.getId() + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/favorites/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Listing not found or already deleted."));
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
