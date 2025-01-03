package com.example.JEE.controllers;

import com.example.JEE.entities.Reservation;
import com.example.JEE.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        if (reservation.getReservationTime() == null) {
            return ResponseEntity.badRequest().body(null); // Check if the reservation time is null
        }

        try {
            Reservation savedReservation = reservationService.createReservation(reservation); // Save reservation
            return ResponseEntity.ok(savedReservation); // Return successful response with saved reservation
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle exceptions
        }
    }


    @GetMapping("/{id}")
    public Optional<Reservation> getReservationById(@PathVariable int id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping("/validate/{id}")
    public ResponseEntity<Reservation> validateReservation(@PathVariable int id) {
        try {
            Reservation validatedReservation = reservationService.validateReservation(id);
            return ResponseEntity.ok(validatedReservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
        try {
            reservationService.sendRefusalEmail(id); // Envoyer l'email de refus d'abord
            reservationService.deleteReservation(id); // Puis supprimer la réservation
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

