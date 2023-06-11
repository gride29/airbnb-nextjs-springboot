package com.gride29.airbnb.clone.backend.reservation.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gride29.airbnb.clone.backend.TestWebConfig;
import com.gride29.airbnb.clone.backend.controllers.ListingController;
import com.gride29.airbnb.clone.backend.dto.ReservationDTO;
import com.gride29.airbnb.clone.backend.models.Listing;
import com.gride29.airbnb.clone.backend.models.Reservation;
import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import com.gride29.airbnb.clone.backend.repository.ReservationRepository;
import com.gride29.airbnb.clone.backend.security.services.ListingService;
import com.gride29.airbnb.clone.backend.security.services.ReservationService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ListingController.class)
@ContextConfiguration(classes = TestWebConfig.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ListingService listingService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        listingRepository.deleteAll();
        reservationRepository.deleteAll();
    }

    @Test
    public void shouldCreateReservation() throws Exception {
        String jsonPayload = "{"
                + "\"userId\": \"User1\","
                + "\"listingId\": \"Listing1\","
                + "\"startDate\": \"2023-05-17T12:00:00\","
                + "\"endDate\": \"2023-05-19T12:00:00\","
                + "\"totalPrice\": 200,"
                + "\"createdAt\": null"
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        List<ReservationDTO> reservations = reservationService.findAll();
        Assertions.assertEquals(1, reservations.size());
        ReservationDTO addedReservation = reservations.get(0);
        Assertions.assertEquals("User1", addedReservation.getUserId());
        Assertions.assertEquals("Listing1", addedReservation.getListingId());
        Assertions.assertEquals(LocalDateTime.of(2023, 5, 17, 12, 0), addedReservation.getStartDate());
        Assertions.assertEquals(LocalDateTime.of(2023, 5, 19, 12, 0), addedReservation.getEndDate());
        Assertions.assertEquals(200, addedReservation.getTotalPrice());
    }

    @Test
    public void shouldReturnAllListings() throws Exception {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("User1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].listingId").value("Listing1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value("User2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].listingId").value("Listing2"));
    }

    @Test
    public void shouldReturnReservationById() throws Exception {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation = testReservations.get(0);

        reservationRepository.save(reservation);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/" + reservation.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("User1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.listingId").value("Listing1"));
    }

    @Test
    public void shouldReturnReservationsByListingId() throws Exception {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/listing/Listing1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("User1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].listingId").value("Listing1"));
    }

    @Test
    public void shouldReturnReservationsByUserId() throws Exception {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/user/User1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("User1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].listingId").value("Listing1"));
    }

    @Test
    public void shouldReturnReservationsByListingOwner() throws Exception {
        LocalDateTime createdAt = LocalDateTime.now();
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

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

        reservation1.setListing(listing1);
        reservation2.setListing(listing2);

        reservation1.setListingId(listing1.getId());
        reservation2.setListingId(listing2.getId());

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/owner/User1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("User1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].listingId").value(listing1.getId()));
    }

    @Test
    public void shouldUpdateReservationById() throws Exception {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation = testReservations.get(0);
        reservationRepository.save(reservation);

        String updatedJsonPayload = "{"
                + "\"userId\": \"User3\","
                + "\"listingId\": \"Listing3\","
                + "\"startDate\": \"2023-05-17T10:00:00\","
                + "\"endDate\": \"2023-05-19T10:00:00\","
                + "\"totalPrice\": 200"
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/reservations/" + reservation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJsonPayload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("User3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.listingId").value("Listing3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2023-05-17T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2023-05-19T10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(200));
    }

    @Test
    public void shouldDeleteReservationById() throws Exception {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation = testReservations.get(0);
        reservationRepository.save(reservation);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reservations/" + reservation.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        List<Reservation> reservations = reservationRepository.findAll();
        Assertions.assertEquals(0, reservations.size());
    }

    @Test
    public void shouldDeleteAllReservations() throws Exception {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reservations"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        List<Reservation> reservations = reservationRepository.findAll();
        Assertions.assertEquals(0, reservations.size());
    }

    private List<Reservation> createTestReservations() {
        LocalDateTime createdAt = LocalDateTime.now();
        Reservation reservation1 = new Reservation(
                "User1",
                "Listing1",
                LocalDateTime.of(2023, 5, 1, 10, 0),
                LocalDateTime.of(2023, 5, 3, 14, 0),
                200,
                createdAt
        );
        Reservation reservation2 = new Reservation(
                "User2",
                "Listing2",
                LocalDateTime.of(2023, 6, 10, 12, 0),
                LocalDateTime.of(2023, 6, 15, 10, 0),
                500,
                createdAt
        );

        return Arrays.asList(reservation1, reservation2);
    }
}