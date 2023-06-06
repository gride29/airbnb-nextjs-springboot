package com.gride29.airbnb.clone.backend.reservation.domain;

import com.gride29.airbnb.clone.backend.TestConfig;
import com.gride29.airbnb.clone.backend.models.Listing;
import com.gride29.airbnb.clone.backend.models.Reservation;
import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import com.gride29.airbnb.clone.backend.repository.ReservationRepository;
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
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        // Clear all reservations before each test
        reservationRepository.deleteAll();
    }

    @Test
    public void shouldReturnMatchingReservationsWithMatchingListingId() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> actualReservations = reservationRepository.findByListingId("Listing1");

        Assertions.assertEquals(1, actualReservations.size());
        Assertions.assertEquals("Listing1", actualReservations.get(0).getListingId());
    }

    @Test
    public void shouldReturnNoMatchingReservationsWithMatchingListingId() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> actualReservations = reservationRepository.findByListingId("InvalidListingId");

        Assertions.assertTrue(actualReservations.isEmpty());
    }

    @Test
    public void shouldDeleteReservationsByIds() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<String> reservationIds = Arrays.asList(reservation1.getId(), reservation2.getId());
        reservationRepository.deleteByIdIn(reservationIds);

        List<Reservation> remainingReservations = reservationRepository.findAll();

        Assertions.assertTrue(remainingReservations.isEmpty());
    }

    @Test
    public void shouldDeleteNoReservationsByIds() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<String> reservationIds = Arrays.asList("InvalidId1", "InvalidId2");
        reservationRepository.deleteByIdIn(reservationIds);

        List<Reservation> remainingReservations = reservationRepository.findAll();

        Assertions.assertEquals(2, remainingReservations.size());
        Assertions.assertEquals("Listing1", remainingReservations.get(0).getListingId());
        Assertions.assertEquals("Listing2", remainingReservations.get(1).getListingId());
    }

    @Test
    public void shouldReturnMatchingReservationsWithMatchingUserId() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> actualReservations = reservationRepository.findByUserId("User1");

        Assertions.assertEquals(1, actualReservations.size());
        Assertions.assertEquals("User1", actualReservations.get(0).getUserId());
    }

    @Test
    public void shouldNotReturnMatchingReservationsWithMatchingUserId() {
        List<Reservation> testReservations = createTestReservations();
        Reservation reservation1 = testReservations.get(0);
        Reservation reservation2 = testReservations.get(1);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> actualReservations = reservationRepository.findByUserId("InvalidUserId");

        Assertions.assertTrue(actualReservations.isEmpty());
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
