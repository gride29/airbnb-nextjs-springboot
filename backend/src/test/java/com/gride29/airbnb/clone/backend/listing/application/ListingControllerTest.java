package com.gride29.airbnb.clone.backend.listing.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gride29.airbnb.clone.backend.TestWebConfig;
import com.gride29.airbnb.clone.backend.controllers.ListingController;
import com.gride29.airbnb.clone.backend.models.Listing;
import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import com.gride29.airbnb.clone.backend.security.services.ListingService;
import org.junit.jupiter.api.Assertions;
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
public class ListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ListingService listingService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        listingRepository.deleteAll();
    }

    @Test
    public void shouldCreateListing() throws Exception {
        String jsonPayload = "{"
                + "\"category\": \"Windmills\","
                + "\"location\": \"AF\","
                + "\"guestCount\": 1,"
                + "\"roomCount\": 1,"
                + "\"bathroomCount\": 1,"
                + "\"imageSrc\": \"testImg.png\","
                + "\"price\": \"50\","
                + "\"title\": \"Cool place\","
                + "\"description\": \"Welcome to my place\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/listings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Welcome to my place"));

        List<Listing> listings = listingRepository.findAll();
        Assertions.assertEquals(1, listings.size());
        Listing addedListing = listings.get(0);
        Assertions.assertEquals("Cool place", addedListing.getTitle());
        Assertions.assertEquals("Welcome to my place", addedListing.getDescription());
    }

    @Test
    public void shouldReturnListingById() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing = testListings.get(0);

        listingRepository.save(listing);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listings/" + listing.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Welcome to my place"));
    }

    @Test
    public void shouldReturnAllListings() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Welcome to my place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Cool place 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Welcome to my place 2"));
    }

    @Test
    public void shouldReturnAllListingsByTitle() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listings/search?title=Cool place"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Welcome to my place"));
    }

    @Test
    public void shouldReturnAllListingsByLocation() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listings/search?location=AF"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Welcome to my place"));
    }

    @Test
    public void shouldReturnAllListingsByCategory() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listings/search?category=Windmills"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Welcome to my place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Windmills"));
    }

    @Test
    public void shouldReturnAllListingsByCategoryAndLocation() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listings/search?category=Windmills&location=AF"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Welcome to my place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Windmills"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].location").value("AF"));
    }

    @Test
    public void shouldReturnAllListingsByCategoryAndLocationAndTitle() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listings/search?category=Windmills&location=AF&title=Cool place"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Welcome to my place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].location").value("AF"));
    }

    @Test
    public void shouldReturnAllListingsByCategoryAndLocationAndTitleAndPrice() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listings/search?category=Windmills&location=AF&title=Cool place&price=50"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Welcome to my place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value("50"));
    }

    @Test
    public void shouldReturnAllListingsByCategoryAndLocationAndTitleAndPriceAndUserId() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listings/search?category=Windmills&location=AF&title=Cool place&price=50&userId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Welcome to my place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value("50"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("1"));
    }

    @Test
    public void shouldReturnAllListingsByUserId() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listings/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Cool place"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Welcome to my place"));
    }

    @Test
    public void shouldUpdateListing() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        Listing savedListing = listingRepository.save(listing1);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/listings/" + savedListing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(listing2)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Cool place 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Welcome to my place 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("100"));
    }

    @Test
    public void shouldDeleteListingById() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);

        Listing savedListing = listingRepository.save(listing1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/listings/" + savedListing.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldDeleteAllListings() throws Exception {
        List<Listing> testListings = createTestListings();
        Listing listing1 = testListings.get(0);
        Listing listing2 = testListings.get(1);

        listingRepository.save(listing1);
        listingRepository.save(listing2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/listings"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
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