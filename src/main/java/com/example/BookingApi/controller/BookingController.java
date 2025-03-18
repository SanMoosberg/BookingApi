package com.example.BookingApi.controller;

import com.example.BookingApi.entity.Booking;
import com.example.BookingApi.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping("/book")
    public Booking bookSlot(@RequestParam Long slotId,
                            @RequestParam int clientId) {
        Booking booking = bookingService.bookSlot(slotId, clientId);
        return booking;
    }

    @DeleteMapping("/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    @GetMapping("/{slotId}")
    public Booking getBookingBySlotId(@PathVariable Long slotId) {
        Booking booking = bookingService.getBookingBySlotId(slotId);
        return booking;
    }
    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<Booking> getBookingByClientId(@PathVariable int clientId) {
        Booking booking = bookingService.getBookingByClientId(clientId);
        if (booking == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(booking);
    }
}
