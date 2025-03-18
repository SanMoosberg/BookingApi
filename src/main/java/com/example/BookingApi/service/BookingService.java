package com.example.BookingApi.service;

import com.example.BookingApi.entity.Booking;
import com.example.BookingApi.entity.SlotStatus;
import com.example.BookingApi.entity.TimeSlot;
import com.example.BookingApi.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TimeSlotService timeSlotService;

    public BookingService(BookingRepository bookingRepository,
                          TimeSlotService timeSlotService) {
        this.bookingRepository = bookingRepository;
        this.timeSlotService = timeSlotService;
    }
    public Booking bookSlot(Long slotId, int clientId) {
        TimeSlot slot = timeSlotService.getSlotById(slotId);
        if (slot.getStatus() != SlotStatus.FREE) {
            String errorMsg = "Slot is not FREE. Current status: " + slot.getStatus();
            throw new RuntimeException(errorMsg);
        }
        Booking booking = new Booking();
        booking.setClientId(clientId);
        booking.setTimeSlot(slot);
        Booking savedBooking = bookingRepository.save(booking);
        slot.setStatus(SlotStatus.BOOKED);
        timeSlotService.saveSlot(slot);
        return savedBooking;
    }

    public Booking getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    String errorMsg = "Booking not found: " + bookingId;
                    return new RuntimeException(errorMsg);
                });
        return booking;
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = getBooking(bookingId);
        TimeSlot slot = booking.getTimeSlot();
        bookingRepository.delete(booking);
        if (slot.getStatus() == SlotStatus.BOOKED) {
            slot.setStatus(SlotStatus.FREE);
            timeSlotService.saveSlot(slot);
        }
    }
    public Booking getBookingBySlotId(Long slotId) {
        Booking booking = bookingRepository.findByTimeSlotId(slotId)
                .orElseThrow(() -> new RuntimeException("Booking not found for slotId: " + slotId));
        return booking;
    }
    public Booking getBookingByClientId(int clientId) {
        return bookingRepository.findByClientId(clientId).orElse(null);
    }


}
