package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class DoctorSchedule {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean available;

    public DoctorSchedule(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = true;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return available;
    }
}
