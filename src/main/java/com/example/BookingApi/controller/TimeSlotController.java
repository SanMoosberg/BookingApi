package com.example.BookingApi.controller;

import com.example.BookingApi.entity.TimeSlot;
import com.example.BookingApi.service.TimeSlotService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @PostMapping("/generate")
    public List<TimeSlot> generateSlots(@RequestParam("date") String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        List<TimeSlot> slots = timeSlotService.generateTimeSlots(date);
        return slots;
    }

    @GetMapping
    public List<TimeSlot> getSlotsByDate(@RequestParam("date") String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        List<TimeSlot> slots = timeSlotService.getSlotsByDate(date);
        return slots;
    }
    @PostMapping("/{slotId}/block")
    public void blockSlot(@PathVariable Long slotId) {
        timeSlotService.blockSlot(slotId);
    }
}
