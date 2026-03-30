package com.example.bookmyshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BookMyShowSystem implements CustomerApi, AdminApi {
    private final Map<String, Movie> movies;
    private final Map<String, Theater> theaters;
    private final Map<String, Show> shows;
    private final Map<String, Ticket> tickets;
    private final PaymentGateway paymentGateway;

    public BookMyShowSystem(PaymentGateway paymentGateway) {
        this.movies = new HashMap<>();
        this.theaters = new HashMap<>();
        this.shows = new HashMap<>();
        this.tickets = new HashMap<>();
        this.paymentGateway = paymentGateway;
    }

    @Override
    public synchronized void addMovie(Movie movie) {
        validateNotNull(movie, "movie");
        movies.put(movie.getMovieId(), movie);
    }

    @Override
    public synchronized void addTheater(Theater theater) {
        validateNotNull(theater, "theater");
        theaters.put(theater.getTheaterId(), theater);
    }

    @Override
    public synchronized void addMovieShow(Show show) {
        validateNotNull(show, "show");
        if (!movies.containsKey(show.getMovieId())) {
            throw new IllegalArgumentException("Movie not found for show: " + show.getMovieId());
        }

        Theater theater = theaters.get(show.getTheaterId());
        if (theater == null) {
            throw new IllegalArgumentException("Theater not found for show: " + show.getTheaterId());
        }

        Auditorium auditorium = theater.findAuditoriumById(show.getAuditoriumId());
        if (auditorium == null) {
            throw new IllegalArgumentException("Auditorium not found in theater for show.");
        }

        shows.put(show.getShowId(), show);
    }

    @Override
    public synchronized Ticket bookTicket(String showId, List<String> seatIds) {
        Show show = requireShow(showId);
        Theater theater = theaters.get(show.getTheaterId());
        Auditorium auditorium = theater.findAuditoriumById(show.getAuditoriumId());

        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("At least one seat is required.");
        }

        Map<String, Seat> seatMap = mapSeats(auditorium.getSeats());
        Set<String> requested = new HashSet<>(seatIds);
        double totalAmount = 0.0;

        for (String seatId : requested) {
            Seat seat = seatMap.get(seatId);
            if (seat == null) {
                throw new IllegalArgumentException("Seat does not exist: " + seatId);
            }
            if (!show.isSeatAvailable(seatId)) {
                throw new IllegalStateException("Seat already booked: " + seatId);
            }
            totalAmount += seat.getBasePrice() + show.incrementForSeatType(seat.getType());
        }

        PaymentResult payment = paymentGateway.pay(new PaymentRequest(showId, totalAmount));
        if (!payment.isSuccess()) {
            throw new IllegalStateException("Payment failed: " + payment.getMessage());
        }

        show.markSeatsBooked(requested);

        String ticketId = "TKT-" + UUID.randomUUID().toString();
        Ticket ticket = new Ticket(ticketId, showId, requested, totalAmount, payment.getTransactionId());
        tickets.put(ticketId, ticket);
        return ticket;
    }

    @Override
    public synchronized List<Theater> showTheater(String city) {
        List<Theater> result = new ArrayList<>();
        for (Theater theater : theaters.values()) {
            if (theater.getCity().equalsIgnoreCase(city)) {
                result.add(theater);
            }
        }
        return result;
    }

    @Override
    public synchronized List<Movie> showMovie(String city) {
        Set<String> movieIdsInCity = new HashSet<>();

        for (Show show : shows.values()) {
            Theater theater = theaters.get(show.getTheaterId());
            if (theater != null && theater.getCity().equalsIgnoreCase(city)) {
                movieIdsInCity.add(show.getMovieId());
            }
        }

        List<Movie> result = new ArrayList<>();
        for (String movieId : movieIdsInCity) {
            Movie movie = movies.get(movieId);
            if (movie != null) {
                result.add(movie);
            }
        }
        return result;
    }

    @Override
    public synchronized RefundReceipt cancelMovie(String ticketId) {
        Ticket ticket = tickets.get(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket not found: " + ticketId);
        }
        if (ticket.getStatus() == TicketStatus.CANCELLED) {
            throw new IllegalStateException("Ticket already cancelled.");
        }

        Show show = requireShow(ticket.getShowId());
        PaymentResult refund = paymentGateway.refund(new RefundRequest(ticket.getPaymentId(), ticket.getTotalAmount()));
        if (!refund.isSuccess()) {
            throw new IllegalStateException("Refund failed: " + refund.getMessage());
        }

        show.releaseSeats(ticket.getSeatIds());
        ticket.markCancelled();

        return new RefundReceipt(ticketId, ticket.getTotalAmount(), refund.getTransactionId());
    }

    private Show requireShow(String showId) {
        Show show = shows.get(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found: " + showId);
        }
        return show;
    }

    private Map<String, Seat> mapSeats(List<Seat> seats) {
        Map<String, Seat> map = new HashMap<>();
        for (Seat seat : seats) {
            map.put(seat.getSeatId(), seat);
        }
        return map;
    }

    private void validateNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " is required.");
        }
    }
}
