package com.example.bookmyshow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Auditorium {
    private final String auditoriumId;
    private final String name;
    private final List<Seat> seats;

    public Auditorium(String auditoriumId, String name, List<Seat> seats) {
        this.auditoriumId = auditoriumId;
        this.name = name;
        this.seats = new ArrayList<>(seats);
    }

    public String getAuditoriumId() {
        return auditoriumId;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return Collections.unmodifiableList(seats);
    }
}
