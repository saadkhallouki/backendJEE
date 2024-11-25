package com.example.JEE.services;

import com.example.JEE.entities.Reservation;
import com.example.JEE.entities.Status;
import com.example.JEE.repositories.ReservationRepository;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    public Reservation createReservation(Reservation reservation) {
        reservation.setStatus(Status.PENDING);
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservationById(int id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }
    public Reservation validateReservation(int id){

        Optional<Reservation> reservationOpt=reservationRepository.findById(id);
        Reservation reservation = reservationOpt.orElseThrow(() -> new OpenApiResourceNotFoundException("Reservation not found for ID: " + id));

        reservation.setStatus(Status.RESERVED);
        SimpleMailMessage message= new SimpleMailMessage();
        message.setSubject("Reservation");
        message.setFrom("anouar7moussaoui@gmail.com");
        message.setTo("Saadkhallouki10@gmail.com");
        message.setText("your reservation code is :"+reservation.getReservationID()+"\n"+
                "table number : " );
        javaMailSender.send(message);
        return reservationRepository.save(reservation);

    }
}

