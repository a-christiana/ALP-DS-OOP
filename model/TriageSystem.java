package model;

public class TriageSystem {
    public static TriageLevel determineTriage(String complaint) {
        complaint = complaint.toLowerCase();

        if (complaint.contains("sesak") ||
            complaint.contains("serangan jantung") ||
            complaint.contains("stroke") ||
            complaint.contains("tidak sadar") ||
            complaint.contains("pendarahan berat") ||
            complaint.contains("kejang")) {
            return TriageLevel.RED;
        }

        if (complaint.contains("demam tinggi") ||
            complaint.contains("patah tulang") ||
            complaint.contains("nyeri berat") ||
            complaint.contains("muntah terus")) {
            return TriageLevel.YELLOW;
        }

        return TriageLevel.GREEN;
    }
}

