package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class DoctorSchedule {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Queue<Appointment> appointmentQueue;
    private final int durationPerPatient = 20;

    public DoctorSchedule(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentQueue = new LinkedList<>();
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

    public Queue<Appointment> getAppointmentQueue() {
        return appointmentQueue;
    }

    public int getMaxPatient() {
        long totalMinutes = Duration.between(startTime, endTime).toMinutes();
        return (int) totalMinutes / durationPerPatient;
    }

    public int getBookedSlot() {
        int count = 0;
        for (Appointment appointment : appointmentQueue) {
            if (appointment.getAppointmentStatus() != AppointmentStatus.CANCELED) {
                count++;
            }
        }
        return count;
    }

    public boolean isFull() {
        return getBookedSlot() >= getMaxPatient();
    }


    public LocalTime getNextAvailableTime() {
        if (isFull()) {
            return null;
        }
        return startTime.plusMinutes(getBookedSlot() * durationPerPatient);
    }

    public boolean addAppointment(Appointment appointment) {
        if (isFull()) {
            return false;
        }
        appointmentQueue.offer(appointment);
        return true;
    }


    public Appointment getNextPendingAppointment() {
        ArrayList<Appointment> sortedQueue = new ArrayList<>(appointmentQueue);
        sortedQueue.sort(Comparator.comparing(Appointment::getAppointmentTime));
        for (Appointment appointment : sortedQueue) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING) {
                return appointment;
            }
        }
        return null;
    }

    public void showDetail() {
        System.out.println("Date : " + date + " | Start : " + startTime + " | End : " + endTime + " | Capacity : " + appointmentQueue.size() + "/" + getMaxPatient());
        if (isFull()) {
            System.out.println("Status : FULL");
        } else {
            System.out.println("Status : Available");
        }
    }
}