package com.example.BookingApi.repository;

import com.example.BookingApi.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByTimeSlotId(Long timeSlotId);
    Optional<Booking> findByClientId(int clientId);
}
