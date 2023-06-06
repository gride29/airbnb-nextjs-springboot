package com.gride29.airbnb.clone.backend.reservation.domain;

import com.gride29.airbnb.clone.backend.TestConfig;
import com.gride29.airbnb.clone.backend.dto.ReservationDTO;
import com.gride29.airbnb.clone.backend.models.Listing;
import com.gride29.airbnb.clone.backend.models.Reservation;
import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import com.gride29.airbnb.clone.backend.repository.ReservationRepository;
import com.gride29.airbnb.clone.backend.security.services.ReservationService;
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
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@ContextConfiguration(classes = TestConfig.class)
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        // Clear all listings and reservations before each test
        reservationRepository.deleteAll();
        listingRepository.deleteAll();
    }

    @Test
    public void shouldReturnAllReservations() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<ReservationDTO> reservations = reservationService.findAll();

        Assertions.assertEquals(2, reservations.size());
        Assertions.assertEquals("Listing1", reservations.get(0).getListingId());
        Assertions.assertEquals("Listing2", reservations.get(1).getListingId());
    }

    @Test
    public void shouldFindReservationById() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);

        reservationRepository.save(reservation1);

        Optional<Reservation> foundReservation = reservationService.findById(reservation1.getId());

        Assertions.assertTrue(foundReservation.isPresent());
        Assertions.assertEquals(reservation1.getId(), foundReservation.get().getId());
    }

    @Test
    public void shouldFindReservationsByListingId() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);

        reservationRepository.save(reservation1);

        List<Reservation> foundReservations = reservationService.findByListingId(reservation1.getListingId());

        Assertions.assertEquals(1, foundReservations.size());
        Assertions.assertEquals(reservation1.getListingId(), foundReservations.get(0).getListingId());
    }

    @Test
    public void shouldFindReservationsByUserId() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);

        reservationRepository.save(reservation1);

        List<Reservation> foundReservations = reservationService.findByUserId(reservation1.getUserId());

        Assertions.assertEquals(1, foundReservations.size());
        Assertions.assertEquals(reservation1.getUserId(), foundReservations.get(0).getUserId());
    }

    @Test
    public void shouldFindReservationsByListingOwner() {
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

        List<Reservation> foundReservations = reservationService.findByListingOwner(reservation1.getListing().getUserId());

        Assertions.assertEquals(1, foundReservations.size());
        Assertions.assertEquals(reservation1.getListing().getUserId(), foundReservations.get(0).getListing().getUserId());
    }

    @Test
    public void shouldUpdateReservation() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);

        reservationRepository.save(reservation1);

        String updatedUserId = "NewUserId";
        String updatedListingId = "NewListingId";

        Reservation updatedReservation = new Reservation();
        updatedReservation.setUserId(updatedUserId);
        updatedReservation.setListingId(updatedListingId);

        Reservation result = reservationService.update(reservation1.getId(), updatedReservation);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedUserId, result.getUserId());
        Assertions.assertEquals(updatedListingId, result.getListingId());
    }

    @Test
    public void shouldDeleteReservationById() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);

        reservationRepository.save(reservation1);

        String reservationId = reservation1.getId();

        reservationService.deleteById(reservationId);

        Optional<Reservation> deletedReservation = reservationRepository.findById(reservationId);

        Assertions.assertFalse(deletedReservation.isPresent());
    }

    @Test
    public void shouldDeleteAllReservations() {
        List<Reservation> testReservations = createTestReservations();

        for (Reservation reservation : testReservations) {
            reservationRepository.save(reservation);
        }

        reservationService.deleteAll();

        List<Reservation> remainingReservations = reservationRepository.findAll();

        Assertions.assertTrue(remainingReservations.isEmpty());
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
