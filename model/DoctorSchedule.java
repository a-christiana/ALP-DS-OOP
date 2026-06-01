package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public boolean isFull() {
        return appointmentQueue.size() >= getMaxPatient();
    }

    public LocalTime getNextAvailableTime() {
        if (isFull()) {
            return null;
        }

        return startTime.plusMinutes(appointmentQueue.size() * durationPerPatient);
    }

    public boolean addAppointment(Appointment appointment) {
        if (isFull()) {
            return false;
        }

        appointmentQueue.offer(appointment);
        return true;
    }

    public Appointment callNextPatient() {
        if (appointmentQueue.isEmpty()) {
            return null;
        }

        return appointmentQueue.poll();
    }

    public void showQueue() {
        if (appointmentQueue.isEmpty()) {
            System.out.println("No queue available.");
            return;
        }

        int no = 1;
        for (Appointment appointment : appointmentQueue) {
            System.out.println(no + ". " + appointment.getAppointmentTime()
                    + " | " + appointment.getPatient().getFullName()
                    + " | " + appointment.getComplain());
            no++;
        }
    }

    public void showDetail() {
        System.out.println("Date : " + date
                + " | Start : " + startTime
                + " | End : " + endTime
                + " | Capacity : " + appointmentQueue.size() + "/" + getMaxPatient());

        if (isFull()) {
            System.out.println("Status : FULL");
        } else {
            System.out.println("Status : Available");
        }
    }
}