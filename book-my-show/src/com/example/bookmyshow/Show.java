package com.example.bookmyshow;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Show {
    private final String showId;
    private final String movieId;
    private final String theaterId;
    private final String auditoriumId;
    private final LocalDateTime startTime;
    private final Map<SeatType, Double> seatTypeIncrement;
    private final Set<String> bookedSeatIds;

    public Show(
            String showId,
            String movieId,
            String theaterId,
            String auditoriumId,
            LocalDateTime startTime,
            Map<SeatType, Double> seatTypeIncrement) {
        this.showId = showId;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.auditoriumId = auditoriumId;
        this.startTime = startTime;
        this.seatTypeIncrement = new EnumMap<>(SeatType.class);
        this.seatTypeIncrement.putAll(seatTypeIncrement);
        this.bookedSeatIds = new HashSet<>();
    }

    public String getShowId() {
        return showId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public String getAuditoriumId() {
        return auditoriumId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Map<SeatType, Double> getSeatTypeIncrement() {
        return Collections.unmodifiableMap(seatTypeIncrement);
    }

    public synchronized boolean isSeatAvailable(String seatId) {
        return !bookedSeatIds.contains(seatId);
    }

    public synchronized void markSeatsBooked(Set<String> seatIds) {
        bookedSeatIds.addAll(seatIds);
    }

    public synchronized void releaseSeats(Set<String> seatIds) {
        bookedSeatIds.removeAll(seatIds);
    }

    public double incrementForSeatType(SeatType seatType) {
        Double value = seatTypeIncrement.get(seatType);
        return value == null ? 0.0 : value;
    }
}
