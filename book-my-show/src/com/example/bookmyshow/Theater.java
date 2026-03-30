package com.example.bookmyshow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Theater {
    private final String theaterId;
    private final String name;
    private final String city;
    private final List<Auditorium> auditoriums;

    public Theater(String theaterId, String name, String city, List<Auditorium> auditoriums) {
        this.theaterId = theaterId;
        this.name = name;
        this.city = city;
        this.auditoriums = new ArrayList<>(auditoriums);
    }

    public String getTheaterId() {
        return theaterId;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public List<Auditorium> getAuditoriums() {
        return Collections.unmodifiableList(auditoriums);
    }

    public Auditorium findAuditoriumById(String auditoriumId) {
        for (Auditorium auditorium : auditoriums) {
            if (auditorium.getAuditoriumId().equals(auditoriumId)) {
                return auditorium;
            }
        }
        return null;
    }
}
