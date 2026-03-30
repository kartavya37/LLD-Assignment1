# Book My Show LLD Demo

This module implements a movie ticket booking system with separate admin/customer APIs, dynamic pricing per show and seat, external payment integration, and cancellation with refund.

## UML Diagram (Schema View)

```mermaid
flowchart TB
    A[Admin API: addMovie, addTheater, addMovieShow]
    C[Customer API: bookTicket, showTheater, showMovie, cancelMovie]

    SYS[BookMyShowSystem]

    M[Movie]
    T[Theater]
    AUD[Auditorium]
    SH[Show: showId + timing + theater + auditorium]
    ST[Seat]
    TK[Ticket]

    PG[PaymentGateway external service]
    MP[MockPaymentGateway]

    A --> SYS
    C --> SYS

    SYS --> M
    SYS --> T
    T --> AUD
    SYS --> SH
    AUD --> ST

    SYS --> TK
    SYS --> PG
    MP --> PG

    SH -. seat availability .-> TK
    SH -. show-wise pricing increment .-> ST
    TK -. cancellation/refund .-> PG
```

## Class Diagram (Code-Level)

```mermaid
classDiagram
    class AdminApi {
        <<interface>>
        +addMovie(movie) void
        +addTheater(theater) void
        +addMovieShow(show) void
    }

    class CustomerApi {
        <<interface>>
        +bookTicket(showId, seats) Ticket
        +showTheater(city) List~Theater~
        +showMovie(city) List~Movie~
        +cancelMovie(ticketId) RefundReceipt
    }

    class BookMyShowSystem {
        -Map~String,Movie~ movies
        -Map~String,Theater~ theaters
        -Map~String,Show~ shows
        -Map~String,Ticket~ tickets
        -PaymentGateway paymentGateway
    }

    class Theater {
        -String theaterId
        -String name
        -String city
        -List~Auditorium~ auditoriums
    }

    class Auditorium {
        -String auditoriumId
        -String name
        -List~Seat~ seats
    }

    class Show {
        -String showId
        -String movieId
        -String theaterId
        -String auditoriumId
        -LocalDateTime startTime
        -Map~SeatType,Double~ seatTypeIncrement
        -Set~String~ bookedSeatIds
    }

    class Seat {
        -String seatId
        -SeatType type
        -double basePrice
    }

    class Movie {
        -String movieId
        -String title
        -String language
        -int durationMinutes
    }

    class Ticket {
        -String ticketId
        -String showId
        -Set~String~ seatIds
        -double totalAmount
        -String paymentId
        -TicketStatus status
    }

    class PaymentGateway {
        <<interface>>
        +pay(request) PaymentResult
        +refund(request) PaymentResult
    }

    class MockPaymentGateway
    class RefundReceipt

    AdminApi <|.. BookMyShowSystem
    CustomerApi <|.. BookMyShowSystem
    PaymentGateway <|.. MockPaymentGateway

    BookMyShowSystem --> Movie
    BookMyShowSystem --> Theater
    BookMyShowSystem --> Show
    BookMyShowSystem --> Ticket
    BookMyShowSystem --> PaymentGateway

    Theater --> Auditorium
    Auditorium --> Seat
    Show --> Theater
```

## APIs Covered

### Customer

- `bookTicket(showId, seats)`
- `showTheater(city)`
- `showMovie(city)`
- `cancelMovie(ticketId)`

### Admin

- `addMovie(movie)`
- `addTheater(theater)`
- `addMovieShow(show)`

## Requirement Coverage

- Two user roles are represented as separate APIs (`CustomerApi`, `AdminApi`).
- A show links movie, theater, auditorium, and timing.
- Theater has multiple auditoriums, and each auditorium has its own seats.
- Booking flow supports selecting show and seats, then calculates total payable amount.
- Pricing is dynamic:
  - seat base price from `Seat`
  - plus show-specific increment by seat type from `Show`
- Payment/refund goes through an external gateway abstraction (`PaymentGateway`).
- Cancellation API releases booked seats and processes refund.

## Build & Run

From project root (`book-my-show`):

```bash
javac src/com/example/bookmyshow/*.java
java -cp src com.example.bookmyshow.App
```

## Demo Notes

`App` demonstrates:

- admin onboarding of movie/theater/show
- customer discovery APIs (`showTheater`, `showMovie`)
- booking seats with dynamic pricing
- cancellation with refund
