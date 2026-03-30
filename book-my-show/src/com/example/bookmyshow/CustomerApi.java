package com.example.bookmyshow;

import java.util.List;

public interface CustomerApi {
    Ticket bookTicket(String showId, List<String> seats);

    List<Theater> showTheater(String city);

    List<Movie> showMovie(String city);

    RefundReceipt cancelMovie(String ticketId);
}
