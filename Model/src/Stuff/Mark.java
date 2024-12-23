package src.Stuff;

import src.Utils.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.UUID;

public class Mark {
    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
    }

    public Mark() {
        this.firstAttestation = 0;
        this.secondAttestation = 0;
        this.finalExam = 0;
    }

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private String markSymbol;

    private static final NavigableMap<Double, String> GRADE_SCALE = new TreeMap<>();

    public boolean saveMark(UUID studentId, UUID courseId){
        String query = """
                insert into mark (id, student_id, cpurse_id, first_attestation, second_attestation, final_examination, final_grade) 
                values (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            UUID markId = UUID.randomUUID();
            double finalGrade = calculateFinalGrade();

            ps.setObject(1, markId);
            ps.setObject(2, studentId);
            ps.setObject(3, courseId);
            ps.setDouble(4, firstAttestation);
            ps.setDouble(5, secondAttestation);
            ps.setDouble(6, finalExam);
            ps.setDouble(7, finalGrade);

            ps.executeUpdate();
            System.out.println("Mark saved");
            return true;
        } catch (SQLException e){
            System.err.println("Failed to save mark: " + e.getMessage());
            return false;
        }
    }

    static {
        GRADE_SCALE.put(94.5, "A");
        GRADE_SCALE.put(89.5, "A-");
        GRADE_SCALE.put(84.5, "B+");
        GRADE_SCALE.put(79.5, "B");
        GRADE_SCALE.put(74.5, "B-");
        GRADE_SCALE.put(69.5, "C+");
        GRADE_SCALE.put(64.5, "C");
        GRADE_SCALE.put(59.5, "D+");
        GRADE_SCALE.put(54.5, "D");
        GRADE_SCALE.put(49.5, "D-");
        GRADE_SCALE.put(0.0, "F");
    }

    public double calculateFinalGrade() {
        return firstAttestation*0.3 + secondAttestation*0.3 + finalExam*0.4;
    }

    public double getFirstAttestation() {
        return firstAttestation;
    }

    public void setFirstAttestation(double firstAttestation) {
        this.firstAttestation = firstAttestation;
    }

    public double getSecondAttestation() {
        return secondAttestation;
    }

    public void setSecondAttestation(double secondAttestation) {
        this.secondAttestation = secondAttestation;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }

    public String getMarkSymbol() {
        double finalGrade = calculateFinalGrade();
        markSymbol = GRADE_SCALE.floorEntry(finalGrade).getValue();
        return markSymbol;
    }

    public void setMarkSymbol(String markSymbol) {
        this.markSymbol = markSymbol;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {");
        sb.append("firstAttestation='").append(firstAttestation).append('\'');
        sb.append(", secondAttestation='").append(secondAttestation).append('\'');
        sb.append(", finalExam=").append(finalExam);
        sb.append(", markSymbol='").append(markSymbol).append('\'');
        sb.append('}');
        return sb.toString();
    }
}