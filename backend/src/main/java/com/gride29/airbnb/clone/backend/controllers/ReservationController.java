package com.gride29.airbnb.clone.backend.controllers;

import com.gride29.airbnb.clone.backend.models.Reservation;
import com.gride29.airbnb.clone.backend.security.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> getAllReservations() {
        return reservationService.findAll();
    }

    @GetMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> getReservationByListingId(@PathVariable("id") String id) {
        return reservationService.findByListingId(id);
    }

    @GetMapping("/reservations/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Reservation> getReservationsByUserId(@PathVariable("id") String id) {
        return reservationService.findByUserId(id);
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Reservation> createReservation(@RequestBody Reservation reservation) {
        return Optional.ofNullable(reservationService
                .save(new Reservation(
                        reservation.getUserId(),
                        reservation.getListingId(),
                        reservation.getStartDate(),
                        reservation.getEndDate(),
                        reservation.getTotalPrice(),
                        LocalDateTime.now())));
    }

    @PutMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Reservation updateReservation(@PathVariable("id") String id, @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservationById(@PathVariable("id") String id) {
        reservationService.deleteById(id);
    }

    @DeleteMapping("/reservations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllReservations() {
        reservationService.deleteAll();
    }
}
