package com.gride29.airbnb.clone.backend.security.services;

import com.gride29.airbnb.clone.backend.models.Reservation;
import com.gride29.airbnb.clone.backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findById(String id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> findByListingId(String listingId) {
        return reservationRepository.findByListingId(listingId);
    }

    public List<Reservation> findByUserId(String userId) {
        return reservationRepository.findByUserId(userId);
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation update(String id, Reservation reservation) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            reservation.setId(id);
            return reservationRepository.save(reservation);
        } else {
            return null;
        }
    }

    public void deleteById(String id) {
        reservationRepository.deleteById(id);
    }

    public void deleteAll() {
        reservationRepository.deleteAll();
    }
}
