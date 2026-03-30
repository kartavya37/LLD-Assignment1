package com.example.bookmyshow;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        BookMyShowSystem system = new BookMyShowSystem(new MockPaymentGateway());

        Movie movie = new Movie("M1", "Inception", "English", 148);
        system.addMovie(movie);

        List<Seat> seatsA1 = Arrays.asList(
                new Seat("A1", SeatType.REGULAR, 180),
                new Seat("A2", SeatType.REGULAR, 180),
                new Seat("P1", SeatType.PREMIUM, 260),
                new Seat("R1", SeatType.RECLINER, 380));

        List<Seat> seatsA2 = Arrays.asList(
                new Seat("B1", SeatType.REGULAR, 150),
                new Seat("B2", SeatType.REGULAR, 150),
                new Seat("Q1", SeatType.PREMIUM, 240));

        Theater theater = new Theater(
                "T1",
                "PVR Downtown",
                "Bangalore",
                Arrays.asList(
                        new Auditorium("AUD1", "Audi 1", seatsA1),
                        new Auditorium("AUD2", "Audi 2", seatsA2)));
        system.addTheater(theater);

        Map<SeatType, Double> increment = new EnumMap<>(SeatType.class);
        increment.put(SeatType.REGULAR, 30.0);
        increment.put(SeatType.PREMIUM, 80.0);
        increment.put(SeatType.RECLINER, 150.0);

        Show show = new Show("S1", "M1", "T1", "AUD1", LocalDateTime.now().plusDays(1), increment);
        system.addMovieShow(show);

        System.out.println("Theaters in Bangalore: " + system.showTheater("Bangalore").size());
        System.out.println("Movies in Bangalore: " + system.showMovie("Bangalore").size());

        Ticket ticket = system.bookTicket("S1", Arrays.asList("A1", "P1"));
        System.out.println("Booked ticket: " + ticket.getTicketId() + " amount=" + ticket.getTotalAmount());

        RefundReceipt refund = system.cancelMovie(ticket.getTicketId());
        System.out.println("Refund processed: " + refund.getRefundTransactionId() + " amount=" + refund.getAmount());
    }
}
